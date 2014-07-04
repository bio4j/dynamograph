package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex, ServiceProvider}

object GoReaders {

  val goTermVertexReader = new VertexReader(GoTerm, GoTermTable, ServiceProvider.dynamoDbExecutor)

  val goNamespaceVertexReader = new VertexReader(GoNamespaces, GoNamespacesTable, ServiceProvider.dynamoDbExecutor)

  val isAEdgeReader = new EdgeReader(IsA, IsATables, ServiceProvider.dynamoDbExecutor)

  val hasPartEdgeReader = new EdgeReader(HasPart, HasPartTables, ServiceProvider.dynamoDbExecutor)

  val partOfEdgeReader = new EdgeReader(PartOf, PartOfTables, ServiceProvider.dynamoDbExecutor)

  val regulatesEdgeReader = new EdgeReader(Regulates, RegulatesTables, ServiceProvider.dynamoDbExecutor)

  val negativelyRegulatesEdgeReader = new EdgeReader(NegativelyRegulates, NegativelyRegulatesTables, ServiceProvider.dynamoDbExecutor)

  val positivelyRegulatesEdgeReader = new EdgeReader(PositivelyRegulates, PositivelyRegulatesTables, ServiceProvider.dynamoDbExecutor)

  val namespaceEdgeReader = new EdgeReader(Namespace, NamespaceTables, ServiceProvider.dynamoDbExecutor)

  def main(args: Array[String]) {
    println(namespaceEdgeReader == positivelyRegulatesEdgeReader)
  }

  def vertexReader[VT <: AnyDynamoVertex](vt : VT) = vertexReaders.find(p => p.vType == vt)

  def edgeReader[ET <: AnyDynamoEdge](et: ET) = edgeReaders.find(p => p.eType == et)

  val vertexReaders = goTermVertexReader :: goNamespaceVertexReader :: Nil

  val edgeReaders = namespaceEdgeReader ::
    positivelyRegulatesEdgeReader ::
    negativelyRegulatesEdgeReader ::
    regulatesEdgeReader ::
    partOfEdgeReader ::
    hasPartEdgeReader ::
    isAEdgeReader :: Nil

}
