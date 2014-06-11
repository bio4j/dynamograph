package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}


trait AnyWriter {
  type writeType

}

trait AnyVertexWriter extends AnyWriter{
  type vertexType <: AnyDynamoVertex
  def write(vertex: vertexType#Rep) : List[writeType]
}

trait AnyEdgeWriter extends AnyWriter{
  type edgeType <: AnyDynamoEdge
  def write(edge: edgeType#Rep) : List[writeType]
}
