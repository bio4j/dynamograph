package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import ohnosequences.tabula.EU
import com.bio4j.dynamograph.model.go.GoImplementation._
import shapeless.HNil


object TableGoImplementation {

  case object GoTermTable               extends VertexTable(GoTerm, "GoTerm", EU)
  case object GoNamespacesTable         extends VertexTable(GoNamespaces, "GoNamespaces", EU)

  case object IsATables                 extends EdgeTables(IsA, "GoIsA", EU)
  case object HasPartTables             extends EdgeTables(HasPart, "GoHasPart",EU)
  case object PartOfTables              extends EdgeTables(PartOf, "GoPartOf", EU)
  case object NegativelyRegulatesTables extends EdgeTables(NegativelyRegulates, "GoNegativelyRegulates", EU)
  case object PositivelyRegulatesTables extends EdgeTables(PositivelyRegulates, "GoPositivelyRegulates", EU)
  case object RegulatesTables           extends EdgeTables(Regulates, "GoRegulates", EU)
  case object NamespaceTables           extends EdgeTables(Namespace, "GoNamespaceEdges", EU)

  val vertexTables = GoTermTable ::
                     GoNamespacesTable ::
                     HNil

  val edgeTables = IsATables ::
                   HasPartTables ::
                   PartOfTables ::
                   NegativelyRegulatesTables ::
                   PositivelyRegulatesTables ::
                   RegulatesTables ::
                   NamespaceTables ::
                   HNil
}
