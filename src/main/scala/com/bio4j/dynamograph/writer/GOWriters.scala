package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GOImplementation._
import com.bio4j.dynamograph.model.go.TableGOImplementation._

object GOWriters {

  class GOVertexWriter extends VertexWriter{
    type vertexType = GOTerm.type
    override val vertexTable = GOTermTable
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
