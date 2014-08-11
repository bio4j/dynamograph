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
  // TODO this shouldn't be needed

  val goNamespacesAttributes = id :~: ∅
  case object GoNamespacesRecord      extends Record(goNamespacesAttributes)
  object GoNamespacesType             extends VertexTypeWithId(id, "GoNamespace", GoNamespacesRecord)


  val edgeAttributes = relationId :~: sourceId :~: targetId :~: ∅
  case object GoEdgeRecord extends Record(edgeAttributes)

  case object HasPartType extends EdgeTypeWithId (
    GoTermType, sourceId, 
    relationId, "HasPartType", GoEdgeRecord, 
    GoTermType, targetId
  ) 
  with ManyIn with ManyOut

  case object IsAType extends EdgeTypeWithId (
    GoTermType, sourceId,
    relationId, "isA", GoEdgeRecord,
    GoTermType, targetId
  ) 
  with ManyIn with ManyOut

  case object PartOfType extends EdgeTypeWithId (
    GoTermType, sourceId,
    relationId, "partOf", GoEdgeRecord, 
    GoTermType, targetId
  ) 
  with ManyIn with ManyOut

  case object NegativelyRegulatesType extends EdgeTypeWithId (
    GoTermType,sourceId,
    relationId, "negativelyRegulates", GoEdgeRecord, 
    GoTermType, targetId
  )
  with ManyIn with ManyOut

  case object PositivelyRegulatesType extends EdgeTypeWithId (
    GoTermType, sourceId,
    relationId, "positivelyRegulates", GoEdgeRecord,
    GoTermType, targetId
  ) 
  with ManyIn with ManyOut

  case object RegulatesType extends EdgeTypeWithId (
    GoTermType, sourceId,
    relationId, "regulates", GoEdgeRecord,
    GoTermType, targetId
  ) 
  with ManyIn with ManyOut

  case object NamespaceType extends EdgeTypeWithId (
    GoTermType, sourceId,
    relationId, "namespace", GoEdgeRecord,
    GoNamespacesType, targetId
  ) 
  with ManyIn with OneOut


}



