package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GOImplementation.GOTerm
import com.bio4j.dynamograph.model.go.TableGOImplementation._

object GOWriters {

  class GOVertexWriter extends VertexWriter[GOTerm.type]{
    override val vertexTable = GOTermTable
  }

  class IsAEdgeWriter extends EdgeWriter[IsATables.type]{
    override val edgeTables = IsATables
  }

  class PartOfEdgeWriter extends EdgeWriter[PartOfTables.type]{
    override val edgeTables = PartOfTables
  }

  class HasPartEdgeWriter extends EdgeWriter[HasPartTables.type]{
    override val edgeTables = HasPartTables
  }

  class PositivelyRegulates extends EdgeWriter[PositivelyRegulatesTables.type]{
    override val edgeTables = PositivelyRegulatesTables
  }

  class NegativelyRegulatesEdgeWriter extends EdgeWriter[NegativelyRegulatesTables.type]{
    override val edgeTables = NegativelyRegulatesTables
  }

}
