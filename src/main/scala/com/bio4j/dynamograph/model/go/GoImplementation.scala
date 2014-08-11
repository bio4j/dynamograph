package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph._
import com.bio4j.dynamograph.model.go.GoSchema._

import com.bio4j.dynamograph.model.go.TableGoImplementation._
import com.bio4j.dynamograph.reader.GoReaders._


object GoImplementation {

  // vertices
  case object GoTerm              extends DynamoVertex(GoTermType, GoTermTable)
  case object GoNamespaces        extends DynamoVertex(GoNamespacesType, GoNamespacesTable)

  //edges
  case object HasPart             extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader,  HasPartType, HasPartTables, GoTerm, GoTermTable, goTermVertexReader)
  case object IsA                 extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader, IsAType, IsATables, GoTerm, GoTermTable, goTermVertexReader)
  case object PartOf              extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader, PartOfType, PartOfTables, GoTerm, GoTermTable, goTermVertexReader)
  case object NegativelyRegulates extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader, NegativelyRegulatesType, NegativelyRegulatesTables, GoTerm, GoTermTable, goTermVertexReader)
  case object PositivelyRegulates extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader, PositivelyRegulatesType, PositivelyRegulatesTables, GoTerm, GoTermTable, goTermVertexReader)
  case object Regulates           extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader, RegulatesType, RegulatesTables, GoTerm, GoTermTable, goTermVertexReader)
  case object Namespace           extends DynamoEdge(
      GoTerm, GoTermTable, goTermVertexReader, NamespaceType, NamespaceTables, GoNamespaces, GoNamespacesTable, goNamespaceVertexReader)
}
