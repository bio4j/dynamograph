package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import ohnosequences.tabula.AnyRegion
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.AnyDynamoVertex
import ohnosequences.typesets.TypeSet

trait AnyVertexWriter extends AnyWriter{
  type Element <: AnyDynamoVertex
}

class VertexWriter[V <: AnyDynamoVertex, R <: AnyRegion, As <: TypeSet, Rw <: TypeSet]
  (val element: V, val vertexTable : VertexTable[V, R, As, Rw]) extends AnyVertexWriter {

  type Element = V

  def write(rep: element.Rep): List[WriteType] =
    List(new PutItemRequest().withTableName(vertexTable.tableName).withItem(rep))
}
