package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.model.go.GoSchema._
import ohnosequences.scarph.{AnyVertexType, AnyEdgeType}

object GoReaders {

  case object goTermVertexReader            extends VertexReader(GoTermTable)
  case object goNamespaceVertexReader       extends VertexReader(GoNamespacesTable)

  case object isAEdgeReader                 extends EdgeReader(IsATables)
  case object hasPartEdgeReader             extends EdgeReader(HasPartTables)
  case object partOfEdgeReader              extends EdgeReader(PartOfTables)
  case object regulatesEdgeReader           extends EdgeReader(RegulatesTables)
  case object negativelyRegulatesEdgeReader extends EdgeReader(NegativelyRegulatesTables)
  case object positivelyRegulatesEdgeReader extends EdgeReader(PositivelyRegulatesTables)
  case object namespaceEdgeReader           extends EdgeReader(NamespaceTables)

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
