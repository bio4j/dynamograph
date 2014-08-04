package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.bio4j.dynamograph.model._

import ohnosequences.tabula._

import com.bio4j.dynamograph.{AnyDynamoVertex}
import com.bio4j.dynamograph.model.GeneralSchema.id
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import ohnosequences.typesets._
import ohnosequences.scarph._

class VertexReader[VT <: AnyVertexTable](val vertexTable: VT, val dbExecutor: AnyDynamoDbExecutor) 
extends AnyVertexReader {

  def read(identifier : id.Raw) : ReturnType = {
    val request = new GetItemRequest()
      .withTableName(vertexTable.tableName)
      .withKey(Map(id.label -> new AttributeValue().withS(identifier)))
    dbExecutor.execute(request)
  }
}
