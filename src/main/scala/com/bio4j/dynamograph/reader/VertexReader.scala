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

trait AnyVertexReader { vertexReader =>
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable: VertexTable

  val dbExecutor: AnyDynamoDbExecutor

  def read(identifier : id.Raw) : Map[String,AttributeValue] = {
    val request = new GetItemRequest()
      .withTableName(vertexTable.table.name)
      .withKey(Map(id.label -> new AttributeValue().withS(identifier)))
    dbExecutor.execute(request)
  }

}

class VertexReader[VT <: AnyVertexTable](val vertexTable: VT, val dbExecutor: AnyDynamoDbExecutor) extends AnyVertexReader {
  type VertexTable = VT

}
