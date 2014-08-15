package com.bio4j.dynamograph.writer

import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoVertex}
import ohnosequences.typesets.TypeSet
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.AnyVertexTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

trait AnyVertexWriter {
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable : VertexTable

  def write(vertex: Map[String,AttributeValue]): List[PutItemRequest] = {
    return List(new PutItemRequest().withTableName(vertexTable.table.name).withItem(vertex))
  }
}

class VertexWriter[VT <: Singleton with AnyVertexTable](val vertexTable : VT) extends AnyVertexWriter {
  type VertexTable = VT
}
