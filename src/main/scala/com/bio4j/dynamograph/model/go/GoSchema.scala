package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import ohnosequences.typesets.Represented._

object GoSchema {

  val goTermAttributes = id :~: name :~: comment :~: definition :~: ∅
  case object GoTermRecord extends Record(goTermAttributes)
  // Vertex Type
  object GoTermType       extends SealedVertexType("GoTerm", GoTermRecord)
  implicit val GoTermType_properties = GoTermType has goTermAttributes

  val goNamespacesAttributes = id :~: ∅
  case object GoNamespacesRecord extends Record(goNamespacesAttributes)
  object GoNamespacesType extends SealedVertexType("GoNamespace", GoNamespacesRecord)
  implicit val GoNamespacesType_properties = GoNamespacesType has goNamespacesAttributes

  // Edge Types
  // TODO sealed edge types
  case object HasPartType             extends ManyToMany (GoTermType, "hasPart", GoTermType)
  case object IsAType                 extends ManyToMany (GoTermType, "isA", GoTermType)
  case object PartOfType              extends ManyToMany (GoTermType, "partOf", GoTermType)
  case object NegativelyRegulatesType extends ManyToMany (GoTermType, "negativelyRegulates", GoTermType)
  case object PositivelyRegulatesType extends ManyToMany (GoTermType, "positivelyRegulates", GoTermType)
  case object RegulatesType           extends ManyToMany (GoTermType, "regulates", GoTermType)
  case object NamespaceType           extends ManyToOne  (GoTermType, "namespace", GoNamespacesType)

}



