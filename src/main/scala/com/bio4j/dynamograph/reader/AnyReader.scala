package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}

trait AnyReader {
  type returnType
}

trait AnyVertexReader extends AnyReader{
  type vertexType <: AnyDynamoVertex
}

trait AnyEdgeReader extends AnyReader{
  type edgeType <: AnyDynamoEdge
  type vertexType
}
