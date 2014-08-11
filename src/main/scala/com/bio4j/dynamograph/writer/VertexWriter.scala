package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model._
import com.bio4j.dynamograph._

import com.amazonaws.services.dynamodbv2.model.AttributeValue

import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._, impl._, impl.actions._

import ohnosequences.typesets._, AnyTag._

import ohnosequences.scarph._

import shapeless._


trait AnyVertexWriter { vertexWriter =>

  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable: VertexTable

  type VertexType = vertexTable.VertexType
  val vertexType: VertexType = vertexTable.vertexType
  type Table = vertexTable.Table
  val table: Table = vertexTable.table
  type Item = vertexTable.VertexItem
  val item: Item = vertexTable.vertexItem

  type Record = vertexTable.Record
  val record : Record = vertexTable.record
  
  type VertexId = vertexTable.VertexId
  val vertexId: VertexId = vertexTable.vertexId
  
  type ContainsId = VertexId ∈ record.Properties
  implicit val containsId = vertexType.containsId 

  // write an item
  def write(vertexItemValue: TaggedWith[Item])(implicit transf: From.Item[Item, SDKRep])
  : List[AnyPutItemAction] = {
    // fails to compile, and it is ok because we need to extract the id from the sealed vertex type
    val tbl: InHashKeyTable[Table] = InHashKeyTable (
      table,
      Active (
        table,
        ServiceProvider.service.account,
        ThroughputStatus(1, 1)
      )
    )
    val pItm: tbl.putItem[Item] = tbl putItem item

    val oaction = pItm withValue vertexItemValue

    val action = InHashKeyTable (
      table,
      Active (
        table,
        ServiceProvider.service.account,
        ThroughputStatus(1, 1)
      )
    ) putItem item withValue vertexItemValue

    List(action) 
  }
}

object AnyVertexWriter{
  type withVertexTableType[VT <: Singleton with AnyVertexTable] =  AnyVertexWriter {type VertexTable = VT}
}

class VertexWriter[VT <: Singleton with AnyVertexTable](val vertexTable: VT) extends AnyVertexWriter {

  type VertexTable = VT
}
