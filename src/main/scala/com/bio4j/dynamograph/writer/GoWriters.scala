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

}
