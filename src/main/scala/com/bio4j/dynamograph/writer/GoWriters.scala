package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.model.go.GoSchema._

object GoWriters {

  case object goTermVertexWriter            extends VertexWriter(GoTermTable)
  case object goNamespaceVertexWriter       extends VertexWriter(GoNamespacesTable)

  case object isAEdgeWriter                 extends EdgeWriter(IsATables)
  case object partOfEdgeWriter              extends EdgeWriter(PartOfTables)
  case object hasPartEdgeWriter             extends EdgeWriter(HasPartTables)
  case object positivelyRegulatesEdgeWriter extends EdgeWriter(PositivelyRegulatesTables)
  case object negativelyRegulatesEdgeWriter extends EdgeWriter(NegativelyRegulatesTables)
  case object regulatesEdgeWriter           extends EdgeWriter(RegulatesTables)
  case object namespaceEdgeWriter           extends EdgeWriter(NamespaceTables)

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
