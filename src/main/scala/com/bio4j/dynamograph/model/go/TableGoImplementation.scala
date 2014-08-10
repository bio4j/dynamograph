package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model._
import shapeless._
import ohnosequences.typesets._
import ohnosequences.tabula._
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.model.go.GoImplementation._


object TableGoImplementation {

  // vertices
  case object GoTermTable extends VertexTable(GoTermType, "GoTerm", EU)
  case object GoNamespacesTable
    extends VertexTable(GoNamespacesType, "GoNamespaces", EU)

  // edges
  // TODO why explicit types needed?? I don't understand this
  case object IsATables extends EdgeTables(
    IsAType, "GoIsA", EU
  )

  case object HasPartTables extends EdgeTables(
    HasPartType, "GoHasPart", EU
  )

  case object PartOfTables extends EdgeTables(
   PartOfType, "GoPartOf", EU
  )

  case object NegativelyRegulatesTables extends EdgeTables(
    NegativelyRegulatesType, "GoNegativelyRegulates", EU
  )

  case object PositivelyRegulatesTables extends EdgeTables(
    PositivelyRegulatesType, "GoPositivelyRegulates", EU
  )

  case object RegulatesTables extends EdgeTables(
    RegulatesType, "GoRegulates", EU
  )

  case object NamespaceTables extends EdgeTables(
    NamespaceType, "GoNamespaceEdges", EU
  )

  val vertexTables = GoTermTable.table ::
                   GoNamespacesTable.table ::
                   HNil

  val edgeTables = IsATables.tables :::
                   HasPartTables.tables :::
                   PartOfTables.tables :::
                   NegativelyRegulatesTables.tables :::
                   PositivelyRegulatesTables.tables :::
                   RegulatesTables.tables :::
                   NamespaceTables.tables
}
