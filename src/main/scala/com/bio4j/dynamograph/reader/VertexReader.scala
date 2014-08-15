package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.bio4j.dynamograph.model.Properties.id
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import com.bio4j.dynamograph.model.AnyVertexTable

trait AnyVertexReader{
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable : VertexTable
  
  val dbExecutor : AnyDynamoDbExecutor
  
    def read(identifier : id.Value) : Map[String,AttributeValue] = {
	  val request = new GetItemRequest()
	    .withTableName(vertexTable.table.name)
	    .withKey(Map(id.label -> new AttributeValue().withS(identifier)))
	  dbExecutor.execute(request)
  }
}

class VertexReader[VT <: Singleton with AnyVertexTable](val vertexTable : VT, override val dbExecutor : AnyDynamoDbExecutor) extends AnyVertexReader{
  type VertexTable = VT
}
