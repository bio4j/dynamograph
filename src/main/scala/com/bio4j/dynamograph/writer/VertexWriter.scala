package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import ohnosequences.tabula.AnyRegion
import scala.collection.JavaConversions._


trait VertexWriter extends AnyVertexWriter {
  type writeType = PutItemRequest

  val vertexTable: VertexTable[vertexType,AnyRegion];
  def write(v: vertexType#Rep) : List[writeType] = {
    return List(new PutItemRequest().withTableName(vertexTable.tableName).withItem(v))
  }
}
