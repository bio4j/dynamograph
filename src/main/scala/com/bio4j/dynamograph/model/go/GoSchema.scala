package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.VertexTypeWithId
import ohnosequences.typesets._
import ohnosequences.typesets.Represented._
import com.bio4j.dynamograph.EdgeTypeWithId

object GoSchema {

  val goTermAttributes = id :~:  name :~: comment :~: definition :~: ∅
  case object GoTermRecord            extends Record(goTermAttributes)
  object GoTermType                   extends VertexTypeWithId(id, "GoTerm", GoTermRecord)
  implicit val GoTermType_properties = GoTermType has goTermAttributes

  val goNamespacesAttributes = id :~: ∅
  case object GoNamespacesRecord      extends Record(goNamespacesAttributes)
  object GoNamespacesType             extends VertexTypeWithId(id, "GoNamespace", GoNamespacesRecord)
  implicit val GoNamespacesType_properties = GoNamespacesType has goNamespacesAttributes


  val edgeAttributes = relationId :~: sourceId :~: targetId :~: ∅
  case object GoEdgeRecord            extends Record(edgeAttributes)

  case object HasPartType             extends EdgeTypeWithId (
    GoTermType,  "HasPartType",          GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object IsAType                 extends EdgeTypeWithId (
    GoTermType, "isA",                  GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object PartOfType              extends EdgeTypeWithId (
    GoTermType, "partOf",               GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object NegativelyRegulatesType extends EdgeTypeWithId (
    GoTermType, "negativelyRegulates",  GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object PositivelyRegulatesType extends EdgeTypeWithId (
    GoTermType, "positivelyRegulates",  GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object RegulatesType           extends EdgeTypeWithId (
    GoTermType, "regulates",            GoEdgeRecord, GoTermType
  ) with ManyIn with ManyOut
  case object NamespaceType           extends EdgeTypeWithId (
    GoTermType, "namespace",            GoEdgeRecord, GoNamespacesType
  ) with ManyIn with OneOut

}



