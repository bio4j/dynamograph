package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._


object TableGoSchema {


  abstract class VertexTable[
    E<: AnyDynamoVertex,
    R <: AnyRegion
  ](
    val tableName : String,
    override val region: R
  ) extends HashKeyTable(tableName, id, region){
    type VertexTpe = E
  }



  abstract class EdgeTables[E<: AnyDynamoEdge, R <: AnyRegion](val tablaName: String, val region: R) {
    class InTable extends CompositeKeyTable(s"${tablaName}_IN", nodeId, relationId, region)
    class OutTable extends CompositeKeyTable(s"${tablaName}_OUT", nodeId, relationId, region)
    class EdgeTable extends HashKeyTable(tablaName, relationId, region)

    type EdgeTpe = E

    val inTable : InTable = new InTable
    val outTable: OutTable = new OutTable
    val edgeTable: EdgeTable = new EdgeTable

  }

}