package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GoSchema._


object GoImplementation {

  // vertices
  case object GoTerm              extends DynamoVertex(GoTermType, GoTermType.attributes)
  case object GoNamespaces        extends DynamoVertex(GoNamespacesType, GoNamespacesType.attributes)

  //edges
  case object HasPart             extends DynamoEdge(GoTerm, HasPartType, GoTerm)
  case object IsA                 extends DynamoEdge(GoTerm, IsAType, GoTerm)
  case object PartOf              extends DynamoEdge(GoTerm, PartOfType, GoTerm)
  case object NegativelyRegulates extends DynamoEdge(GoTerm, NegativelyRegulatesType, GoTerm)
  case object PositivelyRegulates extends DynamoEdge(GoTerm, PositivelyRegulatesType, GoTerm)
  case object Regulates           extends DynamoEdge(GoTerm, RegulatesType, GoTerm)
  case object Namespace           extends DynamoEdge(GoTerm, NamespaceType, GoNamespaces)
}
