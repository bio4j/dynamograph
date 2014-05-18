package com.bio4j.dynamograph.model.go.nodes

import ohnosequences.scarph.Property
import com.bio4j.dynamograph.model.go.nodes.GOTermNamespace.GOTermNamespace


object GOProperties {
  case object id extends Property[String]
  case object name extends Property[String]
  case object namespace extends Property[GOTermNamespace]
  case object definition extends Property[String]

  // Optional
  case object synonyms extends Property[List[String]]
  case object crossRef extends Property[List[String]]
  case object subset extends Property[List[String]]
  case object comment extends Property[String]


}

object GOTermNamespace extends Enumeration{
  type GOTermNamespace = Value
  val CELLULAR_COMONENT = Value("CELLULAR_COMONENT")
  val BIOLOGICAL_PROCESS = Value("BIOLOGICAL_PROCESS")
  val MOLECULAR_FUNCTION = Value("MOLECULAR_FUNCTION")
}
