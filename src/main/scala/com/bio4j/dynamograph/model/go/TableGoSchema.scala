package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{DynamoVertexType, AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import shapeless._
import ohnosequences.tabula.impl.ImplicitConversions.{fromSDKRep, toSDKRep}
import toSDKRep._
import fromSDKRep._
import com.amazonaws.services.dynamodbv2.model.AttributeValue


object TableGoSchema {


  class VertexTable[
    VT <: DynamoVertexType,
    R <: AnyRegion
  ](
    val vt : VT,
    val tableName : String,
    val region: R
  ){
    case object table extends HashKeyTable(tableName, id, region)
    type VertexTpe = VT
    case object vertexItem    extends Item[table.type, vt.VertexRecord](table, vt.record)
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