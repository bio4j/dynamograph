package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.typesets._

trait AnyEdgeTypeWithId extends AnyEdgeType {
  type Id <: Singleton with AnyProperty with AnyProperty.ofValue[String]
  val id : Id
  
  type SourceType <: Singleton with AnyVertexTypeWithId
  val sourceType : SourceType

  type TargetType <: Singleton with AnyVertexTypeWithId
  val targetType : TargetType
  
  type SourceId <: Singleton with AnyProperty with AnyProperty.ofValue[sourceType.id.Value]
  val sourceId: SourceId
  
  type TargetId <: Singleton with AnyProperty with AnyProperty.ofValue[targetType.id.Value]
  val targetId: TargetId
}

abstract class EdgeTypeWithId[
  P <: Singleton with AnyProperty with AnyProperty.ofValue[String],
  S <: Singleton with AnyVertexTypeWithId,
  PS <: Singleton with AnyProperty with AnyProperty.ofValue[S#Id#Value],
  T <: Singleton with AnyVertexTypeWithId,
  PT <: Singleton with AnyProperty with AnyProperty.ofValue[T#Id#Value]
](
  val sourceType: S,
  val sourceId : PS,
  val id: P,
  val label: String,
  val targetType: T,
  val targetId: PT
) extends AnyEdgeTypeWithId {
  type Id = P
  type SourceType = S
  type SourceId = PS
  type TargetType = T
  type TargetId = PT
}

