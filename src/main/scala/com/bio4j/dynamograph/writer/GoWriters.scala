package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.model.go.GoSchema._

object GoWriters {

  val goTermVertexWriter = new VertexWriter(GoTerm, GoTermTable)

  val goNamespaceVertexWriter = new VertexWriter(GoNamespaces, GoNamespacesTable)

  val isAEdgeWriter = new EdgeWriter(IsA, IsATables)

  val partOfEdgeWriter = new EdgeWriter(PartOf, PartOfTables)

  val hasPartEdgeWriter = new EdgeWriter(HasPart, HasPartTables)

  val positivelyRegulatesEdgeWriter = new EdgeWriter(PositivelyRegulates, PositivelyRegulatesTables)

  val negativelyRegulatesEdgeWriter = new EdgeWriter(NegativelyRegulates, NegativelyRegulatesTables)

  val regulatesEdgeWriter = new EdgeWriter(Regulates, RegulatesTables)

  val namespaceEdgeWriter = new EdgeWriter(Namespace, NamespaceTables)

  val edgeWriters = Map(IsAType.label -> isAEdgeWriter,
    PartOfType.label -> partOfEdgeWriter,
    HasPartType.label -> hasPartEdgeWriter,
    PositivelyRegulatesType.label -> positivelyRegulatesEdgeWriter,
    NegativelyRegulatesType.label -> negativelyRegulatesEdgeWriter,
    RegulatesType.label -> regulatesEdgeWriter,
    NamespaceType.label -> namespaceEdgeWriter)


}
