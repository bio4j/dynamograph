package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import shapeless._
import ohnosequences.tabula.impl.ImplicitConversions.{fromSDKRep, toSDKRep}
import toSDKRep._
import fromSDKRep._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph.AnySealedVertexType


object TableGoSchema {

  trait AnyVertexTable { vertexTable => 

    type VertexTpe <: Singleton with AnySealedVertexType
    val vt: VertexTpe

    type Region <: AnyRegion
    val region: Region

    type Table <: AnyHashKeyTable { type Region = vertexTable.Region }
    val table: Table

    // provided implicitly at construction
    val recordValuesAreOK: everyElementOf[VertexTpe#Record#Values]#isOneOf[ValidValues]

    type VertexItem <: AnyItem.ofTable[Table] with AnyItem { type Record = VertexTpe#Record }
    val vertexItem: VertexItem
  }

  class VertexTable[
    VT <: Singleton with AnySealedVertexType,
    R <: AnyRegion
  ](
    val vt : VT,
    val tableName : String,
    val region: R
  )(
    implicit val recordValuesAreOK: everyElementOf[VT#Record#Values]#isOneOf[ValidValues]
  ) 
  extends AnyVertexTable {

    type VertexTpe = VT
    type Region = R

    type Table = Table.type
    val table = Table
    case object Table extends HashKeyTable(tableName, id, region)

    type VertexItem = VertexItem.type
    val vertexItem = VertexItem
    case object VertexItem extends Item[Table, VertexTpe#Record](table, vt.record)
  }



  class EdgeTables[
  ET <: AnyDynamoEdge,
  R <: AnyRegion
  ](
    val et : ET,
    val tablaName: String,
    val region: R
  )
  {
    case object inTable   extends CompositeKeyTable(s"${tablaName}_IN", targetId, relationId, region)
    case object outTable  extends CompositeKeyTable(s"${tablaName}_OUT", sourceId, relationId, region)
    case object edgeTable extends HashKeyTable(tablaName, relationId, region)

    case object inRecord  extends Record(targetId :~: relationId :~: ∅)
    case object outRecord extends Record(sourceId :~: relationId :~: ∅)
    case object record    extends Record(relationId :~: sourceId :~: targetId :~: ∅)

    case object inItem    extends Item(inTable, inRecord)
    case object outItem   extends Item(outTable, outRecord)
    case object item      extends Item(edgeTable, record)

    val tables = inTable :: outTable :: edgeTable :: HNil

    type EdgeTpe = ET
  }

}