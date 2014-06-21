package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.AnyDynamoVertex
import com.bio4j.dynamograph.model.GeneralSchema.id
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor


abstract class VertexReader[VT <: AnyDynamoVertex](val vType: VT,val vertexTable : VertexTable[VT,AnyRegion], val dbExecutor : AnyDynamoDbExecutor) extends AnyVertexReader{
  type returnType = Map[String,AttributeValue]
  type vertexType = VT

  def read(identifier : id.Raw) : returnType = {
    val request = new GetItemRequest()
      .withTableName(vertexTable.tableName)
      .withKey(Map(id.label -> new AttributeValue().withS(identifier)))
    dbExecutor.execute(request)
  }
}
