package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import shapeless._


object TableGoSchema {


  abstract class VertexTable[
    VT <: AnyDynamoVertex,
    R <: AnyRegion,
    As <: ohnosequences.typesets.TypeSet,
    Rw <: ohnosequences.typesets.TypeSet
  ](
    val vt : VT,
    val tableName : String,
    override val region: R,
    val attributes: As
  )(implicit
    val representedAttributes: Represented.By[As, Rw],
    val attributesBound: As << AnyAttribute
  ) extends HashKeyTable(tableName, id, region){
    type VertexTpe = vt.type
    case object vertexItem    extends Item(this, attributes)
  }



  abstract class EdgeTables[
  ET <: AnyDynamoEdge,
  R <: AnyRegion,
  As <: ohnosequences.typesets.TypeSet,
  Rw <: TypeSet
  ](
    val et : ET,
    val tablaName: String,
    val region: R,
    val attributes: As
  )(implicit
    val representedAttributes: Represented.By[As, Rw],
    val attributesBound: As << AnyAttribute
  ) {
    case object inTable   extends CompositeKeyTable(s"${tablaName}_IN", targetId, relationId, region)
    case object outTable  extends CompositeKeyTable(s"${tablaName}_OUT", sourceId, relationId, region)
    case object edgeTable extends HashKeyTable(tablaName, relationId, region)

    case object inItem    extends Item(inTable, targetId :~: relationId :~: ∅)
    case object outItem   extends Item(inTable, targetId :~: relationId :~: ∅)
    case object item      extends Item(edgeTable, attributes)

    val tables = inTable :: outTable :: edgeTable :: HNil

    type EdgeTpe = ET
  }

}