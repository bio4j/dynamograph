package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}
import com.bio4j.dynamograph.model.Properties.id

trait AnyReader {
  type returnType
}

trait AnyVertexReader extends AnyReader{
  type vertexType <: AnyDynamoVertex

  def read(identifier : id.Raw) : returnType
}

trait AnyEdgeReader extends AnyReader{
  type edgeType <: AnyDynamoEdge

  def readOut(vId : id.Raw) : returnType

  def readIn(vId : id.Raw) : returnType
}
