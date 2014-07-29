package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import shapeless.HNil
import ohnosequences.typesets._
import ohnosequences.tabula._
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.model.go.GoImplementation._


object TableGoImplementation {

  case object GoTermTable               extends VertexTable(GoTermType, "GoTerm", EU)
  case object GoNamespacesTable         extends VertexTable(GoNamespacesType, "GoNamespaces", EU)

  case object IsATables                 extends EdgeTables(IsA, "GoIsA", EU)
  case object HasPartTables             extends EdgeTables(HasPart, "GoHasPart",EU)
  case object PartOfTables              extends EdgeTables(PartOf, "GoPartOf", EU)
  case object NegativelyRegulatesTables extends EdgeTables(NegativelyRegulates, "GoNegativelyRegulates", EU)
  case object PositivelyRegulatesTables extends EdgeTables(PositivelyRegulates, "GoPositivelyRegulates", EU)
  case object RegulatesTables           extends EdgeTables(Regulates, "GoRegulates", EU)
  case object NamespaceTables           extends EdgeTables(Namespace, "GoNamespaceEdges", EU)

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
