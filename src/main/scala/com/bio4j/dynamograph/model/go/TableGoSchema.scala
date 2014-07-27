package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import shapeless._
import ohnosequences.scarph.AnyProperty
import ohnosequences.tabula.impl.ImplicitConversions.{fromSDKRep, toSDKRep}
import toSDKRep._
import fromSDKRep._
import com.amazonaws.services.dynamodbv2.model.AttributeValue


object TableGoSchema {


  class VertexTable[
    VT <: AnyDynamoVertex,
    R <: AnyRegion,
    As <: ohnosequences.typesets.TypeSet,
    Rw <: ohnosequences.typesets.TypeSet
  ](
    val vt : VT,
    val tableName : String,
    val region: R,
    val attributes: As
  )(implicit
    val representedAttributes: Represented.By[As, Rw],
    val attributesBound: As << AnyProperty,
    val propertiesHaveValidTypes: everyElementOf[Rw]#isOneOf[ValidValues]
  ){
    case object table extends HashKeyTable(tableName, id, region)
    type VertexTpe = vt.type
    type Attributes = As
    type Representation = Rw
    case object vertexItem    extends Item[table.type, As, Rw](table, attributes)


    def itemRep(sdkRep : Map[String, AttributeValue]) = {
      case object toSDKRepAndPropertyTuple extends Poly1 {
        implicit def default[A <: Singleton with AnyProperty] =
          at[A](a => (sdkRep, a))
      }
      attributes.mapHList(toSDKRepAndPropertyTuple).map(fromSDKRep)
    }
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

    case object inItem    extends Item(inTable, targetId :~: relationId :~: ∅)
    case object outItem   extends Item(outTable, sourceId :~: relationId :~: ∅)
    case object item      extends Item(edgeTable, relationId :~: sourceId :~: targetId :~: ∅)

    val tables = inTable :: outTable :: edgeTable :: HNil

    type EdgeTpe = ET
  }

}