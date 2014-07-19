package com.bio4j.dynamograph.model

import ohnosequences.tabula.Attribute

object Properties {

  case object id              extends Attribute[String]
  case object nodeId          extends Attribute[String]
  case object relationId      extends Attribute[String]
  case object sourceId        extends Attribute[String]
  case object targetId        extends Attribute[String]

  case object name            extends Attribute[String]
  case object definition      extends Attribute[String]
  case object comment         extends Attribute[String]
  case object number          extends Attribute[Long]
  case object scientificName  extends Attribute[String]

 }
