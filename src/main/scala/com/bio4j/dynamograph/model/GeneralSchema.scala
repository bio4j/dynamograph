package com.bio4j.dynamograph.model

import ohnosequences.scarph.Property

object GeneralSchema {

  case object id          extends Property[String]
  case object nodeId      extends Property[String]
  case object relationId  extends Property[String]
  case object sourceId    extends Property[String]
  case object targetId    extends Property[String]

 }
