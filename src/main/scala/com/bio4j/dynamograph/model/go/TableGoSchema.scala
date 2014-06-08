package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}


object TableGoSchema {


  abstract class VertexTable[
    E<: AnyDynamoVertex,
    K <: AnyHash,
    R <: AnyRegion
  ](
    val tableName : String,
    override val key : K,
    override val region: R
  ) extends HashKeyTable(tableName, key, region){
    type VertexTpe = E
  }

  case object nodeId extends Attribute[String]
  case object relationId extends Attribute[String]

  abstract class EdgeTables[E<: AnyDynamoEdge, R <: AnyRegion](val tablaName: String, val region: R) {
    class InTable extends CompositeKeyTable(s"${tablaName}_IN", HashRange[String,nodeId.type,String,relationId.type](nodeId, relationId),region)
    class OutTable extends CompositeKeyTable(s"${tablaName}_OUT", HashRange[String,nodeId.type,String,relationId.type](nodeId, relationId),region)
    class EdgeTable extends HashKeyTable(tablaName, Hash(relationId), region)

    type EdgeTpe = E

    val inTable : InTable = new InTable
    val outTable: OutTable = new OutTable
    val edgeTable: EdgeTable = new EdgeTable

  }

}