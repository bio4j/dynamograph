package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.model.go.TableGoImplementation._


object GoImplementation {

  // vertices
  case object GoTerm              extends DynamoVertex(GoTermType)
  case object GoNamespaces        extends DynamoVertex(GoNamespacesType)

  //edges
  case object HasPart             extends DynamoEdge(GoTerm, GoTermTable, HasPartType, GoTerm, GoTermTable)
  case object IsA                 extends DynamoEdge(GoTerm, GoTermTable, IsAType, GoTerm, GoTermTable)
  case object PartOf              extends DynamoEdge(GoTerm, GoTermTable, PartOfType, GoTerm, GoTermTable)
  case object NegativelyRegulates extends DynamoEdge(GoTerm, GoTermTable, NegativelyRegulatesType, GoTerm, GoTermTable)
  case object PositivelyRegulates extends DynamoEdge(GoTerm, GoTermTable, PositivelyRegulatesType, GoTerm, GoTermTable)
  case object Regulates           extends DynamoEdge(GoTerm, GoTermTable, RegulatesType, GoTerm, GoTermTable)
  case object Namespace           extends DynamoEdge(GoTerm, GoTermTable, NamespaceType, GoNamespaces, GoNamespacesTable)
}
