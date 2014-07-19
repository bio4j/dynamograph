package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
 import com.bio4j.dynamograph.model.Properties._
import shapeless._


object TableGoSchema {


  abstract class VertexTable[
    VT <: AnyDynamoVertex,
    R <: AnyRegion
  ](
    val vt : VT,
    val tableName : String,
    override val region: R
  ) extends HashKeyTable(tableName, id, region){
    type VertexTpe = vt.type
  }



  abstract class EdgeTables[ET <: AnyDynamoEdge, R <: AnyRegion](val et : ET,val tablaName: String, val region: R) {
    case object inTable extends CompositeKeyTable(s"${tablaName}_IN", nodeId, relationId, region)
    case object outTable extends CompositeKeyTable(s"${tablaName}_OUT", nodeId, relationId, region)
    case object edgeTable extends HashKeyTable(tablaName, relationId, region)

    type EdgeTpe = et.type

    val tables = inTable :: outTable :: edgeTable :: HNil
  }

}