package com.bio4j.dynamograph

import ohnosequences.scarph.AnySealedEdgeType
import ohnosequences.typesets._
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.scarph.From
import ohnosequences.scarph.To


trait AnyEdgeTypeWithId extends AnySealedEdgeType {
  type Id = relationId.type
  val id : Id = relationId
  val containId : Id ∈ record.Properties
  type SourceId = sourceId.type
  type TargetId = targetId.type
  val srcId : SourceId = sourceId
  val tgtId : TargetId = targetId
  val containSourceId : SourceId ∈ record.Properties
  val containTargetId : TargetId ∈ record.Properties
  val idLookup: Lookup[record.Raw, relationId.Rep]
  val sourceLookup: Lookup[record.Raw, SourceId#Rep]
  val targetLookup: Lookup[record.Raw, TargetId#Rep]
  type SourceType <: AnyVertexTypeWithId
  type TargetType <: AnyVertexTypeWithId
}

abstract class EdgeTypeWithId[
  S <: AnyVertexTypeWithId,
  R <: Singleton with AnyRecord,
  T <: AnyVertexTypeWithId
](
  val sourceType: S,
  val label: String,
  val record: R,
  val targetType: T
)(
  implicit val containId : relationId.type ∈ R#Properties,
  val idLookup: Lookup[R#Raw, relationId.Rep],
  val containSourceId : sourceId.type ∈ R#Properties,
  val sourceLookup: Lookup[R#Raw, sourceId.Rep],
  val containTargetId : targetId.type ∈ R#Properties,
  val targetLookup: Lookup[R#Raw, targetId.Rep]
)extends AnyEdgeTypeWithId with From[S] with To[T] {
  type Record = R
  type SourceType = S
  type TargetType = T
}