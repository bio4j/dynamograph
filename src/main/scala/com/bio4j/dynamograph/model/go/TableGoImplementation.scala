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
    extends EdgeTables[GoTermTable.type, IsA.type, GoTermTable.type, EU.type](
      GoTermTable, IsA, GoTermTable, "GoIsA", EU
    )

  case object HasPartTables 
    extends EdgeTables[GoTermTable.type, HasPart.type, GoTermTable.type, EU.type](
      GoTermTable, HasPart, GoTermTable, "GoHasPart", EU
    )

  case object PartOfTables              
    extends EdgeTables[GoTermTable.type, PartOf.type, GoTermTable.type, EU.type](
      GoTermTable, PartOf, GoTermTable, "GoPartOf", EU
    )

  case object NegativelyRegulatesTables 
    extends EdgeTables[GoTermTable.type, NegativelyRegulates.type, GoTermTable.type, EU.type](
      GoTermTable, NegativelyRegulates, GoTermTable,"GoNegativelyRegulates", EU
    )

  case object PositivelyRegulatesTables 
    extends EdgeTables[GoTermTable.type, PositivelyRegulates.type, GoTermTable.type, EU.type](
      GoTermTable, PositivelyRegulates, GoTermTable, "GoPositivelyRegulates", EU
    )

  case object RegulatesTables           
    extends EdgeTables[GoTermTable.type, Regulates.type, GoTermTable.type, EU.type](
      GoTermTable, Regulates, GoTermTable, "GoRegulates", EU
    )

  case object NamespaceTables           
    extends EdgeTables[GoTermTable.type, Namespace.type, GoNamespacesTable.type, EU.type](
      GoTermTable, Namespace, GoNamespacesTable, "GoNamespaceEdges", EU
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
