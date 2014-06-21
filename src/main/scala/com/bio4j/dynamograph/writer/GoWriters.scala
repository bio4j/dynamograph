package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._

object GoWriters {

  object GoTermVertexWriter extends VertexWriter(GoTerm, GoTermTable)

  object GoNamespaceVertexWriter extends VertexWriter(GoNamespaces, GoNamespacesTable)

  object IsAEdgeWriter extends EdgeWriter(IsA, IsATables)

  object PartOfEdgeWriter extends EdgeWriter(PartOf, PartOfTables)

  object HasPartEdgeWriter extends EdgeWriter(HasPart, HasPartTables)

  object PositivelyRegulatesEdgeWriter extends EdgeWriter(PositivelyRegulates, PositivelyRegulatesTables)

  object NegativelyRegulatesEdgeWriter extends EdgeWriter(NegativelyRegulates, NegativelyRegulatesTables)

  object RegulatesEdgeWriter extends EdgeWriter(Regulates, RegulatesTables)

  object NamespaceEdgeWriter extends EdgeWriter(Namespace, NamespaceTables)

}
