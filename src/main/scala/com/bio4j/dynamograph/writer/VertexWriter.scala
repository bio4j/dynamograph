package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import ohnosequences.tabula.AnyRegion
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.AnyDynamoVertex


abstract class VertexWriter[VT <:AnyDynamoVertex, R <:AnyRegion](val vType: VT, val vertexTable : VertexTable[VT,R]) extends AnyVertexWriter {
  type writeType = PutItemRequest
  type vertexType = VT

  def write(vertex: vertexType#Rep) : List[writeType] = {
    return List(new PutItemRequest().withTableName(vertexTable.tableName).withItem(vertex))
  }
}
