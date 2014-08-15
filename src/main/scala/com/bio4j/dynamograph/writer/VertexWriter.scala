package com.bio4j.dynamograph.writer

import ohnosequences.tabula.AnyRegion
import ohnosequences.typesets.TypeSet
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.AnyVertexTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.bio4j.dynamograph.default._

trait AnyVertexWriter extends AnyWriter {
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable : VertexTable

  def write(vertex: Representation): List[PutItemRequest] = {
    return List(new PutItemRequest().withTableName(vertexTable.table.name).withItem(vertex))
  }
}

class VertexWriter[VT <: Singleton with AnyVertexTable](val vertexTable : VT) extends AnyVertexWriter {
  type VertexTable = VT
}
