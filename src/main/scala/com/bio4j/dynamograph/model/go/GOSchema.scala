package com.bio4j.dynamograph.model.go

import ohnosequences.scarph.{VertexType, ManyToMany, Property}
import com.bio4j.dynamograph.model.go.GOTermNamespace.GOTermNamespace

object GOSchema {

  // Properties
  case object id extends Property[String]
  case object name extends Property[String]
  case object namespace extends Property[GOTermNamespace]
  case object definition extends Property[String]


  case object synonyms extends Property[List[String]]
  case object crossRef extends Property[List[String]]
  case object subset extends Property[List[String]]
  case object comment extends Property[String]


  // Vertex Type
  object GOTermType extends VertexType("GOTerm"){
    implicit val termId         = this has id
    implicit val termName       = this has name
    implicit val termNamespace  = this has namespace
    implicit val termDefinition = this has definition

    // Optionals
    implicit val termComment    = this has comment

  }


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
