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
  case object IsATables 
    extends EdgeTables(GoTermTable, IsAType, GoTermTable, "GoIsA", EU)

  case object HasPartTables 
    extends EdgeTables(
      GoTermTable, HasPartType, GoTermTable, "GoHasPart", EU
    )

  case object PartOfTables              
    extends EdgeTables(
      GoTermTable, PartOfType, GoTermTable, "GoPartOf", EU
    )

  case object NegativelyRegulatesTables 
    extends EdgeTables(
      GoTermTable, NegativelyRegulatesType, GoTermTable,"GoNegativelyRegulates", EU
    )

  case object PositivelyRegulatesTables 
    extends EdgeTables(
      GoTermTable, PositivelyRegulatesType, GoTermTable, "GoPositivelyRegulates", EU
    )

  case object RegulatesTables           
    extends EdgeTables(
      GoTermTable, RegulatesType, GoTermTable, "GoRegulates", EU
    )

  case object NamespaceTables           
    extends EdgeTables(
      GoTermTable, NamespaceType, GoNamespacesTable, "GoNamespaceEdges", EU
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
