package com.bio4j.dynamograph.model

import ohnosequences.scarph._
import ohnosequences.typesets._
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._
import shapeless._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._

/*
This type creates a link between a sealed vertex type and a DynamoDB table
*/
trait AnyVertexTable { vertexTable => 

  type VertexType <: Singleton with AnySealedVertexType
  val vertexType: VertexType

  type Region <: AnyRegion
  val region: Region

  type Table <: Singleton with AnyTable.inRegion[vertexTable.Region] with 
                AnyHashKeyTable.withKey[VertexId]

  val table: Table

  type Record = vertexType.Record
  val record: Record = vertexType.record

  type VertexId <: Singleton with AnyProperty.ofValue[String]
  val vertexId: VertexId

  // provided implicitly at construction
  val recordValuesAreOK: everyElementOf[VertexType#Record#Values]#isOneOf[ValidValues]

  type VertexItem <:  Singleton with AnyItem with 
                      AnyItem.ofTable[vertexTable.Table] with 
                      AnyItem.withRecord[vertexTable.Record]

  val vertexItem: VertexItem
}

object AnyVertexTable {

  type withVertexType[V <: Singleton with AnySealedVertexType] = AnyVertexTable { type VertexType = V }
}

class VertexTable[VT <: Singleton with AnySealedVertexType, R <: AnyRegion](
  val vertexType : VT,
  val tableName : String,
  val region: R
)
(implicit 
  val recordValuesAreOK: everyElementOf[VT#Record#Values]#isOneOf[ValidValues]
) 
extends AnyVertexTable {

  type VertexType = VT
  type Region = R

  type VertexId = id.type
  val vertexId = id

  type Record = VertexType#Record
  val record = vertexType.record

  type Table = Table.type
  val table = Table
  case object Table extends HashKeyTable(tableName, vertexId, region)

  type VertexItem = VertexItem.type
  val vertexItem = VertexItem
  case object VertexItem extends Item[Table, Record](table, record)
}

