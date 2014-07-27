package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge, ServiceProvider}

object GoReaders {

  case object goTermVertexReader            extends VertexReader(GoTerm, GoTermTable, ServiceProvider.dynamoDbExecutor)
  case object goNamespaceVertexReader       extends VertexReader(GoNamespaces, GoNamespacesTable, ServiceProvider.dynamoDbExecutor)

  case object isAEdgeReader                 extends EdgeReader(IsA, IsATables, ServiceProvider.dynamoDbExecutor)
  case object hasPartEdgeReader             extends EdgeReader(HasPart, HasPartTables, ServiceProvider.dynamoDbExecutor)
  case object partOfEdgeReader              extends EdgeReader(PartOf, PartOfTables, ServiceProvider.dynamoDbExecutor)
  case object regulatesEdgeReader           extends EdgeReader(Regulates, RegulatesTables, ServiceProvider.dynamoDbExecutor)
  case object negativelyRegulatesEdgeReader extends EdgeReader(NegativelyRegulates, NegativelyRegulatesTables, ServiceProvider.dynamoDbExecutor)
  case object positivelyRegulatesEdgeReader extends EdgeReader(PositivelyRegulates, PositivelyRegulatesTables, ServiceProvider.dynamoDbExecutor)
  case object namespaceEdgeReader           extends EdgeReader(Namespace, NamespaceTables, ServiceProvider.dynamoDbExecutor)

  val vertexReaders = Map[AnyDynamoVertex, AnyVertexReader] (
    GoTerm      -> goTermVertexReader,
    GoNamespaces -> goNamespaceVertexReader
  )

  val edgeReaders = Map[AnyDynamoEdge, AnyEdgeReader](
    namespaceEdgeReader.edgeType            -> namespaceEdgeReader,
    positivelyRegulatesEdgeReader.edgeType  -> positivelyRegulatesEdgeReader,
    negativelyRegulatesEdgeReader.edgeType  -> negativelyRegulatesEdgeReader,
    regulatesEdgeReader.edgeType            -> regulatesEdgeReader,
    partOfEdgeReader.edgeType               -> partOfEdgeReader,
    hasPartEdgeReader.edgeType              -> hasPartEdgeReader,
    isAEdgeReader.edgeType                  -> isAEdgeReader
  )

}
