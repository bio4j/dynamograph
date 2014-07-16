package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}
import com.bio4j.dynamograph.model.GeneralSchema.id
import com.amazonaws.services.dynamodbv2.model.AttributeValue

trait AnyReader {
  type ReturnType
}

trait AnyVertexReader extends AnyReader{
  type ReturnType = Map[String,AttributeValue]
  type vertexType <: AnyDynamoVertex

  def read(identifier : id.Raw) : ReturnType
}

trait AnyEdgeReader extends AnyReader{
  type ReturnType = List[Map[String,AttributeValue]]
  type EdgeType <: AnyDynamoEdge

  def readOut(vId : id.Raw) : ReturnType

  def readIn(vId : id.Raw) : ReturnType
}
