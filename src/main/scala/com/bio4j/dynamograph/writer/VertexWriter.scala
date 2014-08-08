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

  type Table = vertexTable.Table
  val table: Table = vertexTable.table
  type Item = vertexTable.VertexItem
  val item: Item = vertexTable.vertexItem

  type Record = vertexTable.Record
  val record = vertexTable.record

  // write an item
  def write(vertexItemValue: TaggedWith[Item])(implicit transf: From.Item[Item, SDKRep]): List[AnyPutItemAction] = {
    // fails to compile, and it is ok because we need to extract the id from the sealed vertex type
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

class VertexWriter[VT <: Singleton with AnyVertexTable](val vertexTable: VT) extends AnyVertexWriter {

  type VertexTable = VT
}
