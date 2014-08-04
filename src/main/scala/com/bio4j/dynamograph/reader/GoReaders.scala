package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge, ServiceProvider}
import com.bio4j.dynamograph.model.go.GoSchema.{GoTermType, GoNamespacesType}

object GoReaders {

  case object goTermVertexReader            extends VertexReader(GoTermTable, ServiceProvider.dynamoDbExecutor)
  case object goNamespaceVertexReader       extends VertexReader( GoNamespacesTable, ServiceProvider.dynamoDbExecutor)

  case object isAEdgeReader                 extends EdgeReader(IsATables, ServiceProvider.dynamoDbExecutor)
  case object hasPartEdgeReader             extends EdgeReader(HasPartTables, ServiceProvider.dynamoDbExecutor)
  case object partOfEdgeReader              extends EdgeReader(PartOfTables, ServiceProvider.dynamoDbExecutor)
  case object regulatesEdgeReader           extends EdgeReader(RegulatesTables, ServiceProvider.dynamoDbExecutor)
  case object negativelyRegulatesEdgeReader extends EdgeReader(NegativelyRegulatesTables, ServiceProvider.dynamoDbExecutor)
  case object positivelyRegulatesEdgeReader extends EdgeReader(PositivelyRegulatesTables, ServiceProvider.dynamoDbExecutor)
  case object namespaceEdgeReader           extends EdgeReader(NamespaceTables, ServiceProvider.dynamoDbExecutor)

  val vertexReaders = Map[AnyDynamoVertex, AnyVertexReader] (
    GoTerm      -> goTermVertexReader,
    GoNamespaces -> goNamespaceVertexReader
  )

  val edgeReaders = Map[AnyDynamoEdge, AnyEdgeReader](
    Namespace            -> namespaceEdgeReader,
    PositivelyRegulates  -> positivelyRegulatesEdgeReader,
    NegativelyRegulates  -> negativelyRegulatesEdgeReader,
    Regulates            -> regulatesEdgeReader,
    PartOf               -> partOfEdgeReader,
    HasPart              -> hasPartEdgeReader,
    IsA                  -> isAEdgeReader
  )

}
