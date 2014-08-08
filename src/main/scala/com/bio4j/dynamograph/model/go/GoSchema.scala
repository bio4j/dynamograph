package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._
import ohnosequences.typesets.Represented._

object GoSchema {

  val goTermAttributes = id :~: name :~: comment :~: definition :~: ∅
  case object GoTermRecord            extends Record(goTermAttributes)
  object GoTermType                   extends SealedVertexType("GoTerm", GoTermRecord)
  implicit val GoTermType_properties = GoTermType has goTermAttributes

  val goNamespacesAttributes = id :~: ∅
  case object GoNamespacesRecord      extends Record(goNamespacesAttributes)
  object GoNamespacesType             extends SealedVertexType("GoNamespace", GoNamespacesRecord)
  implicit val GoNamespacesType_properties = GoNamespacesType has goNamespacesAttributes


  val edgeAttributes = relationId :~: sourceId :~: targetId :~: ∅
  case object GoEdgeRecord            extends Record(edgeAttributes)

  case object HasPartType             extends SealedEdgeType (
    GoTermType,  "HasPartType",          GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object IsAType                 extends SealedEdgeType (
    GoTermType, "isA",                  GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object PartOfType              extends SealedEdgeType (
    GoTermType, "partOf",               GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object NegativelyRegulatesType extends SealedEdgeType (
    GoTermType, "negativelyRegulates",  GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object PositivelyRegulatesType extends SealedEdgeType (
    GoTermType, "positivelyRegulates",  GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object RegulatesType           extends SealedEdgeType (
    GoTermType, "regulates",            GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object NamespaceType           extends SealedEdgeType (
    GoTermType, "namespace",            GoEdgeRecord, GoNamespacesType
  ) with ManyIn with OneOut

}



