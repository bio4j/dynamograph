package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.AnyDynamoVertex
import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import ohnosequences.tabula.{AnyRegion, AnyAttribute}
import com.bio4j.dynamograph.model.go.GOImplementation.GOTerm
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.model.go.TableGOImplementation.GOTermTable


trait VertexWriter extends AnyVertexWriter {
  type writeType = PutItemRequest

  val vertexTable: VertexTable[vertexType,AnyRegion];
  def write(v: vertexType#Rep) : List[writeType] = {
    return List(new PutItemRequest().withTableName(vertexTable.tableName).withItem(v))
  }
}
