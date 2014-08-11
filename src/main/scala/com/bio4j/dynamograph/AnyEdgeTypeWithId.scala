package com.bio4j.dynamograph

import ohnosequences.scarph.AnySealedEdgeType
import ohnosequences.typesets._
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.scarph.From
import ohnosequences.scarph.To


trait AnyEdgeTypeWithId extends AnySealedEdgeType {

  type Id <: Singleton with AnyProperty with AnyProperty.ofValue[String]
  val id : Id

  val containsId : Id ∈ Record#Properties
  val idLookup: Lookup[Record#Raw, Id#Rep]
  
  type SourceType <: AnyVertexTypeWithId

  // the id of the source
  type SourceId <: Singleton with AnyProperty with AnyProperty.ofValue[SourceType#Id#Value]
  val sourceId: SourceId

  type TargetType <: AnyVertexTypeWithId

  type TargetId <: Singleton with AnyProperty with AnyProperty.ofValue[TargetType#Id#Value]
  val targetId: TargetId

  val containsSourceId: SourceId ∈ Record#Properties
  val sourceLookup: Lookup[Record#Raw, SourceId#Rep]
  val containsTargetId: TargetId ∈ Record#Properties
  val targetLookup: Lookup[Record#Raw, TargetId#Rep]
}

class EdgeTypeWithId[
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
)(implicit 
  val containsId: P ∈ R#Properties,
  val containSourceId: PS ∈ R#Properties,
  val containTargetId: PT ∈ R#Properties,
  val idLookup: Lookup[R#Raw, P#Rep],
  val sourceLookup: Lookup[R#Raw, PS#Rep],
  val targetLookup: Lookup[R#Raw, PT#Rep]
)
extends AnyEdgeTypeWithId with From[S] with To[T] {

  type Id = P
  type Record = R
  type SourceType = S
  type SourceId = PS
  type TargetType = T
  type TargetId = PT
}