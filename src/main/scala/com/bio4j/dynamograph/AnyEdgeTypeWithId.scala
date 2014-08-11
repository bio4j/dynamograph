package com.bio4j.dynamograph

import ohnosequences.scarph.AnySealedEdgeType
import ohnosequences.typesets._
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.scarph.From
import ohnosequences.scarph.To


trait AnyEdgeTypeWithId extends AnySealedEdgeType {

  type Id <: Singleton with AnyProperty with AnyProperty.ofValue[String]
  val id : Id

  implicit val containsId: (Id ∈ record.Properties)
  implicit val idLookup: Lookup[Record#Raw, id.Rep]
  
  type SourceType <: AnyVertexTypeWithId

  // the id of the source
  type SourceId <: Singleton with AnyProperty with AnyProperty.ofValue[sourceType.id.Value]
  val sourceId: SourceId

  type TargetType <: AnyVertexTypeWithId

  type TargetId <: Singleton with AnyProperty with AnyProperty.ofValue[targetType.id.Value]
  val targetId: TargetId

  implicit val containsSourceId: (SourceId ∈ record.Properties)
  implicit val sourceLookup: Lookup[record.Values, sourceId.Rep]
  implicit val containsTargetId: (TargetId ∈ record.Properties)
  implicit val targetLookup: Lookup[record.Values, targetId.Rep]
}

abstract class EdgeTypeWithId[
  S <: Singleton with AnyVertexTypeWithId,
  PS <: Singleton with AnyProperty with AnyProperty.ofValue[S#Id#Value],
  P <: Singleton with AnyProperty with AnyProperty.ofValue[String],
  R <: Singleton with AnyRecord,
  T <: Singleton with AnyVertexTypeWithId,
  PT <: Singleton with AnyProperty with AnyProperty.ofValue[T#Id#Value]
](
  val sourceType: S,
  val sourceId: PS,
  val id: P,
  val label: String,
  val record: R,
  val targetType: T,
  val targetId: PT
)
extends AnyEdgeTypeWithId with From[S] with To[T] {

  type Id = P
  type Record = R
  type SourceType = S
  type SourceId = PS
  type TargetType = T
  type TargetId = PT
}