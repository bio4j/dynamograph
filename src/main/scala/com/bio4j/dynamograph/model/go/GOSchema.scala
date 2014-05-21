package com.bio4j.dynamograph.model.go

import ohnosequences.scarph.{VertexType, ManyToMany, Property}
import com.bio4j.dynamograph.model.go.GOTermNamespace.GOTermNamespace

object GOSchema {

  // Properties
  case object id extends Property[String]
  case object name extends Property[String]
  case object namespace extends Property[GOTermNamespace]
  case object definition extends Property[String]

  case object comment extends Property[String]


  // Vertex Type
  object GOTermType extends VertexType("GOTerm")

  implicit val GoTermType_id         = GOTermType has id
  implicit val GoTermType_name       = GOTermType has name
  implicit val GoTermType_namespace  = GOTermType has namespace
  implicit val GoTermType_definition = GOTermType has definition

    // Optionals
  implicit val termComment    = GOTermType has comment




  // Edge Types
  case object HasPartType extends ManyToMany (GOTermType, "has_part", GOTermType)

  case object IsAType extends ManyToMany (GOTermType, "is_a", GOTermType)

  case object NegativelyRegulatesType extends ManyToMany (GOTermType, "positively_regulates", GOTermType)

  case object PartOfType extends ManyToMany (GOTermType, "part_of", GOTermType)

  case object PositivelyRegulatesType extends ManyToMany (GOTermType, "negatively_regulates", GOTermType)

}

object GOTermNamespace extends Enumeration{
  type GOTermNamespace = Value
  val CELLULAR_COMONENT = Value("CELLULAR_COMONENT")
  val BIOLOGICAL_PROCESS = Value("BIOLOGICAL_PROCESS")
  val MOLECULAR_FUNCTION = Value("MOLECULAR_FUNCTION")
}



