package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import ohnosequences.tabula.Attribute
import com.bio4j.dynamograph.model.GeneralSchema.id

object GOSchema {

  // Properties
  case object name extends Attribute[String]
  case object definition extends Attribute[String]

  case object comment extends Attribute[String]


  // Vertex Type
  object GOTermType extends VertexType("GOTerm")

  implicit val GoTermType_id         = GOTermType has id
  implicit val GoTermType_name       = GOTermType has name
  implicit val GoTermType_definition = GOTermType has definition

    // Optionals
  implicit val termComment    = GOTermType has comment

  object GONamespacesType extends VertexType("GONamespace")


  // Edge Types
  case object HasPartType extends ManyToMany (GOTermType, "has_part", GOTermType)

  case object IsAType extends ManyToMany (GOTermType, "is_a", GOTermType)

  case object NegativelyRegulatesType extends ManyToMany (GOTermType, "positively_regulates", GOTermType)

  case object PartOfType extends ManyToMany (GOTermType, "part_of", GOTermType)

  case object PositivelyRegulatesType extends ManyToMany (GOTermType, "negatively_regulates", GOTermType)

  // Namespace edge types
  case object NamespaceType extends ManyToOne (GOTermType, "namespace", GONamespacesType)

}



