package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import ohnosequences.tabula.Attribute
import com.bio4j.dynamograph.model.GeneralSchema.id

object GoSchema {

  // Properties
  case object name extends Attribute[String]
  case object definition extends Attribute[String]
  case object comment extends Attribute[String]
  


  // Vertex Type
  object GoTermType extends VertexType("GoTerm")

  implicit val GoTermType_id         = GoTermType has id
  implicit val GoTermType_name       = GoTermType has name
  implicit val GoTermType_definition = GoTermType has definition
  implicit val GoTermType_comment    = GoTermType has comment

  object GoNamespacesType extends VertexType("GoNamespace")

  implicit val GoNamespacesType_id         = GoNamespacesType has id

  // Edge Types
  case object HasPartType extends ManyToMany (GoTermType, "hasPart", GoTermType)

  case object IsAType extends ManyToMany (GoTermType, "isA", GoTermType)

  case object PartOfType extends ManyToMany (GoTermType, "partOf", GoTermType)

  case object NegativelyRegulatesType extends ManyToMany (GoTermType, "positivelyRegulates", GoTermType)

  case object PositivelyRegulatesType extends ManyToMany (GoTermType, "negativelyRegulates", GoTermType)

  case object RegulatesType extends ManyToMany (GoTermType, "regulates", GoTermType)

  case object NamespaceType extends ManyToOne (GoTermType, "namespace", GoNamespacesType)

}



