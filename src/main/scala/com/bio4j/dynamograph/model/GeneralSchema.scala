package com.bio4j.dynamograph.model

import ohnosequences.tabula.Attribute

object GeneralSchema {

  case object id extends Attribute[String]

  case object nodeId extends Attribute[String]
  case object relationId extends Attribute[String]

  case object sourceId extends Attribute[String]
  case object targetId extends Attribute[String]

 }
