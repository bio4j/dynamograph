package com.bio4j.dynamograph.model.go

import ohnosequences.scarph._
import com.bio4j.dynamograph.model.Properties._
import ohnosequences.typesets._
import com.bio4j.dynamograph.model.go.TableGoImplementation.IsATables
import com.bio4j.dynamograph.VertexTypeWithId
import com.bio4j.dynamograph.EdgeTypeWithId

object GoSchema {

  // Vertex Type
  object GoTermType       extends VertexTypeWithId(id, "GoTerm")
  implicit val GoTermType_id         = GoTermType has id
  implicit val GoTermType_name       = GoTermType has name
  implicit val GoTermType_definition = GoTermType has definition
  implicit val GoTermType_comment    = GoTermType has comment

  object GoNamespacesType extends VertexTypeWithId(id, "GoNamespace")
  implicit val GoNamespacesType_id         = GoNamespacesType has id

  // Edge Types
  case object HasPartType             extends EdgeTypeWithId (GoTermType, sourceId, relationId, "hasPart", GoTermType, targetId) 
  	with ManyIn with ManyOut
  case object IsAType                 extends EdgeTypeWithId (GoTermType, sourceId, relationId, "isA", GoTermType, targetId) 
  	with ManyIn with ManyOut
  case object PartOfType              extends EdgeTypeWithId (GoTermType, sourceId, relationId, "partOf", GoTermType, targetId) 
  	with ManyIn with ManyOut
  case object NegativelyRegulatesType extends EdgeTypeWithId (GoTermType, sourceId, relationId, "negativelyRegulates", GoTermType, targetId) 
  	with ManyIn with ManyOut
  case object PositivelyRegulatesType extends EdgeTypeWithId (GoTermType, sourceId, relationId, "positivelyRegulates", GoTermType, targetId) 
  	with ManyIn with ManyOut
  case object RegulatesType           extends EdgeTypeWithId (GoTermType, sourceId, relationId, "regulates", GoTermType, targetId) 
  	with ManyIn with ManyOut
  case object NamespaceType           extends EdgeTypeWithId (GoTermType, sourceId, relationId, "namespace", GoNamespacesType, targetId) 
  	with ManyIn with ManyOut

}
