package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.go.TableGoImplementation._

object GoWriters {

  object GoTermVertexWriter extends VertexWriter{
    type vertexType = GoTerm.type
    override val vertexTable = GoTermTable
  }

  object GoNamespaceVertexWriter extends VertexWriter{
    type vertexType = GoNamespaces.type
    override val vertexTable = GoNamespacesTable
  }

  object IsAEdgeWriter extends EdgeWriter{
    type edgeType = IsA.type
    override val edgeTables = IsATables
  }

  object PartOfEdgeWriter extends EdgeWriter{
    type edgeType = PartOf.type
    override val edgeTables = PartOfTables
  }

  object HasPartEdgeWriter extends EdgeWriter{
    type edgeType = HasPart.type
    override val edgeTables = HasPartTables
  }

  object PositivelyRegulatesEdgeWriter extends EdgeWriter{
    type edgeType = PositivelyRegulates.type
    override val edgeTables = PositivelyRegulatesTables
  }

  object NegativelyRegulatesEdgeWriter extends EdgeWriter{
    type edgeType = NegativelyRegulates.type
    override val edgeTables = NegativelyRegulatesTables
  }

  object RegulatesEdgeWriter extends EdgeWriter{
    type edgeType = Regulates.type
    override val edgeTables = RegulatesTables
  }

  object NamespaceEdgeWriter extends EdgeWriter{
    type edgeType = Namespace.type
    override val edgeTables = NamespaceTables
  }

  val vertexWriters = GoTermVertexWriter ::
    GoNamespaceVertexWriter :: Nil

  val edgeWriters =
    IsAEdgeWriter ::
    PartOfEdgeWriter ::
    HasPartEdgeWriter ::
    PositivelyRegulates ::
    NegativelyRegulatesEdgeWriter :: Nil




}
