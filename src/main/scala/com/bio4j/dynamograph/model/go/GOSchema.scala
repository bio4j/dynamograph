package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._

object GOSchema {

  // Properties
  case object id extends Property[String]
  case object name extends Property[String]
  case object definition extends Property[String]

  case object comment extends Property[String]


  // Vertex Type
  object GOTermType extends VertexType("GOTerm")

  implicit val GoTermType_id         = GOTermType has id
  implicit val GoTermType_name       = GOTermType has name
  implicit val GoTermType_definition = GOTermType has definition

    // Optionals
  implicit val termComment    = GOTermType has comment

  object GONamespaceType extends VertexType("GONamespace")


  // Edge Types
  case object HasPartType extends ManyToMany (GOTermType, "has_part", GOTermType)

  case object IsAType extends ManyToMany (GOTermType, "is_a", GOTermType)

  case object NegativelyRegulatesType extends ManyToMany (GOTermType, "positively_regulates", GOTermType)

  case object PartOfType extends ManyToMany (GOTermType, "part_of", GOTermType)

  case object PositivelyRegulatesType extends ManyToMany (GOTermType, "negatively_regulates", GOTermType)

  // Namespace edge types
  case object CellularComponentType extends ManyToOne (GOTermType, "cellular_component", GONamespaceType)

  case object MolecularFunctionType extends ManyToOne (GOTermType, "molecular_function", GONamespaceType)

  case object BiologicalProcessType extends ManyToOne (GOTermType, "biological_function", GONamespaceType)


}



