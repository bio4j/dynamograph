package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GOSchema._
import com.bio4j.dynamograph.model.go.GOTermNamespace.GOTermNamespace
import ohnosequences.scarph.Property


object GOImplementation {

  // vertices
  case object GOTerm extends DynamoVertex[GOTermType.type](GOTermType) {

    implicit val _namespace : GetProperty[namespace.type] = new GetProperty[namespace.type](namespace) {
      def apply(rep: Rep): p.Raw = GOTermNamespace.withName(rep.getAttributeValue(p.label))
    }
  }

  //edges
  case object HasPart extends DynamoEdge(GOTerm, HasPartType, GOTerm)

  case object IsA extends DynamoEdge(GOTerm, IsAType, GOTerm)

  case object NegativelyRegulates extends DynamoEdge(GOTerm, NegativelyRegulatesType, GOTerm)

  case object PartOf extends DynamoEdge(GOTerm, PartOfType, GOTerm)

  case object PositivelyRegulates extends DynamoEdge(GOTerm, PositivelyRegulatesType, GOTerm)

}
