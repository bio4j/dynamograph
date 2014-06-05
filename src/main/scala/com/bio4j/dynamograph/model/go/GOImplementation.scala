package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoRawVertex, DynamoRawEdge, DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GOSchema._
import com.bio4j.dynamograph.model.go.GOTermNamespace.GOTermNamespace
import ohnosequences.scarph.{ManyToOne, Property}


object GOImplementation {

  // vertices
  case object GOTerm extends DynamoVertex[GOTermType.type](GOTermType) {

  }

  case object GONamespace extends DynamoVertex[GONamespaceType.type](GONamespaceType)

  //edges
  case object HasPart extends DynamoEdge(GOTerm, HasPartType, GOTerm)

  case object IsA extends DynamoEdge(GOTerm, IsAType, GOTerm)

  case object NegativelyRegulates extends DynamoEdge(GOTerm, NegativelyRegulatesType, GOTerm)

  case object PartOf extends DynamoEdge(GOTerm, PartOfType, GOTerm)

  case object PositivelyRegulates extends DynamoEdge(GOTerm, PositivelyRegulatesType, GOTerm)

  case object CellularComponent extends DynamoEdge(GONamespace, CellularComponentType, GOTerm)

  case object MolecularFunction extends DynamoEdge(GONamespace, MolecularFunctionType, GOTerm)

  case object BiologicalProcess extends DynamoEdge(GONamespaceType, BiologicalProcessType, GOTermType)
}
