package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import ohnosequences.tabula.EU
import com.bio4j.dynamograph.model.go.GoImplementation._
import shapeless.HNil
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import com.bio4j.dynamograph.model.go.GoSchema._


object TableGoImplementation {

  val edgeStandardAttributes = relationId :~:
    sourceId :~:
    targetId :~:
    ∅

  case object GoTermTable               extends VertexTable(GoTerm, "GoTerm", EU, id :~: name :~: definition :~: comment :~: ∅)
  case object GoNamespacesTable         extends VertexTable(GoNamespaces, "GoNamespaces", EU, id :~: ∅)

  case object IsATables                 extends EdgeTables(IsA, "GoIsA", EU, edgeStandardAttributes)
  case object HasPartTables             extends EdgeTables(HasPart, "GoHasPart",EU, edgeStandardAttributes)
  case object PartOfTables              extends EdgeTables(PartOf, "GoPartOf", EU, edgeStandardAttributes)
  case object NegativelyRegulatesTables extends EdgeTables(NegativelyRegulates, "GoNegativelyRegulates", EU, edgeStandardAttributes)
  case object PositivelyRegulatesTables extends EdgeTables(PositivelyRegulates, "GoPositivelyRegulates", EU, edgeStandardAttributes)
  case object RegulatesTables           extends EdgeTables(Regulates, "GoRegulates", EU, edgeStandardAttributes)
  case object NamespaceTables           extends EdgeTables(Namespace, "GoNamespaceEdges", EU, edgeStandardAttributes)

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
