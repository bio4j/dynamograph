package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.model.go.GoSchema._

object GoWriters {

  // NOTE: we can extend `VertexWriter`/`EdgeWriter`, so why not to have nice singletons?
  case object goTermVertexWriter            extends VertexWriter(GoTermType, GoTermTable)
  case object goNamespaceVertexWriter       extends VertexWriter(GoNamespacesType, GoNamespacesTable)

  case object isAEdgeWriter                 extends EdgeWriter(IsA, IsATables)
  case object partOfEdgeWriter              extends EdgeWriter(PartOf, PartOfTables)
  case object hasPartEdgeWriter             extends EdgeWriter(HasPart, HasPartTables)
  case object positivelyRegulatesEdgeWriter extends EdgeWriter(PositivelyRegulates, PositivelyRegulatesTables)
  case object negativelyRegulatesEdgeWriter extends EdgeWriter(NegativelyRegulates, NegativelyRegulatesTables)
  case object regulatesEdgeWriter           extends EdgeWriter(Regulates, RegulatesTables)
  case object namespaceEdgeWriter           extends EdgeWriter(Namespace, NamespaceTables)

  // NOTE: see the related note in GoMapper.
  // So to convert a string to a typed writer, 
  // at some point we have to make an explicit mapping between them
  // Also you actually don't need here anything from a specific wirter type (no type information),
  // because you will use just it's `write` method (which is now in the common `AnyWriter` trait)
  val vertexWritersMap = Map[String, AnyVertexWriter] (
    GoTermType.label              -> goTermVertexWriter,
    GoNamespacesType.label        -> goNamespaceVertexWriter
  )

  val edgeWritersMap = Map[String, AnyEdgeWriter] (
    IsAType.label                 -> isAEdgeWriter,
    PartOfType.label              -> partOfEdgeWriter,
    HasPartType.label             -> hasPartEdgeWriter,
    PositivelyRegulatesType.label -> positivelyRegulatesEdgeWriter,
    NegativelyRegulatesType.label -> negativelyRegulatesEdgeWriter,
    RegulatesType.label           -> regulatesEdgeWriter,
    NamespaceType.label           -> namespaceEdgeWriter
  )

}
