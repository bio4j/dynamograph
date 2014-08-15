package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.ServiceProvider
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.model.go.GoSchema._
import ohnosequences.scarph.{AnyVertexType, AnyEdgeType}

object GoReaders {

  val ddb = ServiceProvider.dynamoDbExecutor

  case object goTermVertexReader            extends VertexReader(GoTermTable, ddb)
  case object goNamespaceVertexReader       extends VertexReader(GoNamespacesTable, ddb)

  case object isAEdgeReader                 extends EdgeReader(IsATables, ddb)
  case object hasPartEdgeReader             extends EdgeReader(HasPartTables, ddb)
  case object partOfEdgeReader              extends EdgeReader(PartOfTables, ddb)
  case object regulatesEdgeReader           extends EdgeReader(RegulatesTables, ddb)
  case object negativelyRegulatesEdgeReader extends EdgeReader(NegativelyRegulatesTables, ddb)
  case object positivelyRegulatesEdgeReader extends EdgeReader(PositivelyRegulatesTables, ddb)
  case object namespaceEdgeReader           extends EdgeReader(NamespaceTables, ddb)

  val vertexReaders = Map[AnyVertexType, AnyVertexReader] (
    GoTermType      -> goTermVertexReader,
    GoNamespacesType -> goNamespaceVertexReader
  )

  val edgeReaders = Map[AnyEdgeType, AnyEdgeReader](
    NamespaceType            -> namespaceEdgeReader,
    PositivelyRegulatesType  -> positivelyRegulatesEdgeReader,
    NegativelyRegulatesType  -> negativelyRegulatesEdgeReader,
    PositivelyRegulatesType  -> regulatesEdgeReader,
    PartOfType               -> partOfEdgeReader,
    HasPartType              -> hasPartEdgeReader,
    IsAType                  -> isAEdgeReader
  )

}
