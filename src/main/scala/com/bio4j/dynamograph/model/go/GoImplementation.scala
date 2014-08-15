package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.reader.GoReaders._


object GoImplementation {

  // vertices
  case object GoTerm              extends DynamoVertex(GoTermType, GoTermTable , goTermVertexReader)
  case object GoNamespaces        extends DynamoVertex(GoNamespacesType, GoNamespacesTable, goNamespaceVertexReader)

  //edges
  case object HasPart             extends DynamoEdge(
    GoTerm, HasPartType, GoTerm, HasPartTables, hasPartEdgeReader)
  case object IsA                 extends DynamoEdge(
    GoTerm, IsAType, GoTerm, IsATables, isAEdgeReader)
  case object PartOf              extends DynamoEdge(
    GoTerm, PartOfType, GoTerm, PartOfTables, partOfEdgeReader)
  case object NegativelyRegulates extends DynamoEdge(
    GoTerm, NegativelyRegulatesType, GoTerm, NegativelyRegulatesTables, negativelyRegulatesEdgeReader)
  case object PositivelyRegulates extends DynamoEdge(
    GoTerm, PositivelyRegulatesType, GoTerm, PositivelyRegulatesTables, positivelyRegulatesEdgeReader)
  case object Regulates           extends DynamoEdge(
    GoTerm, RegulatesType, GoTerm, RegulatesTables, regulatesEdgeReader)
  case object Namespace           extends DynamoEdge(
    GoTerm, NamespaceType, GoNamespaces, NamespaceTables, namespaceEdgeReader)
}
