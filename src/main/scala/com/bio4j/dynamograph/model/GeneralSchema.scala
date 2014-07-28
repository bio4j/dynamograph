package com.bio4j.dynamograph.model

import ohnosequences.typesets._

object GeneralSchema {

  case object id          extends Property[String]
  case object nodeId      extends Property[String]
  case object relationId  extends Property[String]
  case object sourceId    extends Property[String]
  case object targetId    extends Property[String]
  case object name        extends Property[String]
  case object definition  extends Property[String]
  case object comment     extends Property[String]

 }
