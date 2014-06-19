package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._

object GOWriters {

  class GOVertexWriter extends VertexWriter{
    type vertexType = GoTerm.type
    override val vertexTable = GoTermTable
  }

  class GONamespaceVertexWriter extends VertexWriter{
    type vertexType = GoNamespaces.type
    override val vertexTable = GoNamespacesTable
  }

  class IsAEdgeWriter extends EdgeWriter{
    type edgeType = IsA.type
    override val edgeTables = IsATables
  }

  class PartOfEdgeWriter extends EdgeWriter{
    type edgeType = PartOf.type
    override val edgeTables = PartOfTables
  }

  class HasPartEdgeWriter extends EdgeWriter{
    type edgeType = HasPart.type
    override val edgeTables = HasPartTables
  }

  class PositivelyRegulates extends EdgeWriter{
    type edgeType = PositivelyRegulates.type
    override val edgeTables = PositivelyRegulatesTables
  }

  class NegativelyRegulatesEdgeWriter extends EdgeWriter{
    type edgeType = NegativelyRegulates.type
    override val edgeTables = NegativelyRegulatesTables
  }

}
