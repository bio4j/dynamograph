package com.bio4j.dynamograph

import ohnosequences.typesets._
import ohnosequences.scarph.AnySealedVertexType


trait AnyVertexTypeWithId extends AnySealedVertexType {

  type Id <: Singleton with AnyProperty with AnyProperty.ofValue[String]
  val id: Id

  implicit val containsId: Id ∈ Record#Properties
}

abstract class VertexTypeWithId [
  P <: Singleton with AnyProperty with AnyProperty.ofValue[String],
  R <: Singleton with AnyRecord
](
  val id: P,
  val label: String,
  val record: R
)
extends AnyVertexTypeWithId {

  type Record = R
  type Id = P

  implicit val containsId: Id ∈ record.Properties
}