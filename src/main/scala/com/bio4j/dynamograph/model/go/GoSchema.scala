package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import ohnosequences.typesets.Represented._
import com.bio4j.dynamograph.DynamoVertexType

object GoSchema {



  // Vertex Type
  object GoTermType       extends DynamoVertexType("GoTerm"){
    val attributes = id :~: name :~: comment :~: definition :~: ∅
    case object record extends Record(attributes)
  }
  implicit val GoTermType_properties = GoTermType has GoTermType.attributes


  object GoNamespacesType extends DynamoVertexType("GoNamespace"){
    val attributes = id :~: ∅
    case object record extends Record(attributes)
  }
  implicit val GoNamespacesType_properties = GoNamespacesType has GoNamespacesType.attributes

  // Edge Types
  case object HasPartType             extends ManyToMany (GoTermType, "hasPart", GoTermType)
  case object IsAType                 extends ManyToMany (GoTermType, "isA", GoTermType)
  case object PartOfType              extends ManyToMany (GoTermType, "partOf", GoTermType)
  case object NegativelyRegulatesType extends ManyToMany (GoTermType, "negativelyRegulates", GoTermType)
  case object PositivelyRegulatesType extends ManyToMany (GoTermType, "positivelyRegulates", GoTermType)
  case object RegulatesType           extends ManyToMany (GoTermType, "regulates", GoTermType)
  case object NamespaceType           extends ManyToOne  (GoTermType, "namespace", GoNamespacesType)

}



