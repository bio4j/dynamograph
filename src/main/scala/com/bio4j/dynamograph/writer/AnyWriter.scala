package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}


trait AnyWriter {
  type writeType

}

trait AnyVertexWriter[V<: AnyDynamoVertex] extends AnyWriter{
  def write(vertex: V#Rep) : List[writeType]
}

trait AnyEdgeWriter[E<: AnyDynamoEdge] extends AnyWriter{
  def write(edge: E#Rep) : List[writeType]
}
