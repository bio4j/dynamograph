package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge, ServiceProvider}
import com.bio4j.dynamograph.model.go.GoSchema.{GoTermType, GoNamespacesType}
import ohnosequences.scarph.AnySealedVertexType
import com.bio4j.dynamograph.AnyVertexTypeWithId

object GoReaders {

  case object goTermVertexReader            extends VertexReader(GoTermTable)
  case object goNamespaceVertexReader       extends VertexReader(GoNamespacesTable)
}
