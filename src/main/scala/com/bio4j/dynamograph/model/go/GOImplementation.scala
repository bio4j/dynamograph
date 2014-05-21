package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GOSchema._


object GOImplementation {

  // vertices
  case object GOTerm extends DynamoVertex[GOTermType.type](GOTermType) {

    implicit val _id : GetProperty[id.type] = unsafeGetProperty(id)

    implicit val _name : GetProperty[name.type] = unsafeGetProperty(name)

    implicit val _namespace : GetProperty[namespace.type] = unsafeGetProperty(namespace)

    implicit val _definition : GetProperty[definition.type] = unsafeGetProperty(definition)

    implicit val _comment : GetProperty[comment.type] = unsafeGetProperty(comment)
  }

  //edges
  case object HasPart extends DynamoEdge(GOTerm, HasPartType, GOTerm)

  case object IsA extends DynamoEdge(GOTerm, IsAType, GOTerm)

  case object NegativelyRegulates extends DynamoEdge(GOTerm, NegativelyRegulatesType, GOTerm)

  case object PartOf extends DynamoEdge(GOTerm, PartOfType, GOTerm)

  case object PositivelyRegulates extends DynamoEdge(GOTerm, PositivelyRegulatesType, GOTerm)

}
