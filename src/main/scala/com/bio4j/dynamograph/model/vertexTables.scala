package com.bio4j.dynamograph.model

import ohnosequences.scarph._
import ohnosequences.typesets._
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._
import shapeless._
import com.bio4j.dynamograph._

/*
This type creates a link between a sealed vertex type and a DynamoDB table
*/
trait AnyVertexTable { vertexTable => 

  type VertexType <: Singleton with AnyVertexTypeWithId
  val vertexType: VertexType
  
  type Region <: AnyRegion
  val region: Region

  type Table <: Singleton with AnyTable.inRegion[vertexTable.Region] with AnyHashKeyTable with 
                AnyHashKeyTable.withKey[VertexId]

  val table: Table

  type Record = vertexType.Record
  val record: Record = vertexType.record

  type VertexId = vertexType.Id
  val vertexId: VertexId = vertexType.id
  
  type VertexItem <:  Singleton with AnyItem with 
                      AnyItem.ofTable[Table] with 
                      AnyItem.withRecord[Record]

  val vertexItem: VertexItem

  implicit val containsId: (vertexType.Id ∈ record.Properties)
  // provided implicitly at construction
  implicit val recordValuesAreOK: everyElementOf[Record#Raw]#isOneOf[ValidValues]
  implicit val hashKeyIsInRecord: (table.HashKey ∈ record.Properties)
}

object AnyVertexTable {

  type withVertexType[V <: Singleton with AnyVertexTypeWithId] = AnyVertexTable { type VertexType = V }
}

abstract class VertexTable[VT <: Singleton with AnyVertexTypeWithId, R <: AnyRegion](
  val vertexType : VT,
  val tableName : String,
  val region: R
)
extends AnyVertexTable {

  type VertexType = VT
  type Region = R

  type Table = Table.type
  val table = Table
  case object Table extends HashKeyTable(tableName, vertexId, region)

  type VertexItem = vItem.type
  val vertexItem = vItem
  case object vItem extends Item[Table,Record](table, record)
  
  
}

