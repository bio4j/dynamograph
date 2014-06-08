package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.AnyDynamoVertex
import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import ohnosequences.tabula.{AnyRegion, AnyHash}
import com.bio4j.dynamograph.model.go.GOImplementation.GOTerm
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.model.go.TableGOImplementation.GOTermTable


trait VertexWriter[V<: AnyDynamoVertex] {
  type writeType = PutItemRequest

  val vertexTable: VertexTable[V,AnyHash,AnyRegion];
  def write(v: V#Rep) : List[writeType] = {
    return List(new PutItemRequest().withTableName(vertexTable.tableName).withItem(v.attributes))
  }
}
