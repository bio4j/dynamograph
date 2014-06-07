package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GOSchema._


object GOImplementation {

  // vertices
  case object GOTerm extends DynamoVertex(GOTermType)

  case object GONamespaces extends DynamoVertex(GONamespacesType)

  //edges
  case object HasPart extends DynamoEdge(GOTerm, HasPartType, GOTerm)

  case object IsA extends DynamoEdge(GOTerm, IsAType, GOTerm)

  case object NegativelyRegulates extends DynamoEdge(GOTerm, NegativelyRegulatesType, GOTerm)

  case object PartOf extends DynamoEdge(GOTerm, PartOfType, GOTerm)

  case object PositivelyRegulates extends DynamoEdge(GOTerm, PositivelyRegulatesType, GOTerm)

  case object Namespace extends DynamoEdge(GOTerm, NamespaceType, GONamespaces)
}
