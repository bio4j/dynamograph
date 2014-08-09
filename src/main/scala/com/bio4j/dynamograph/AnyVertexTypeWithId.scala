package com.bio4j.dynamograph

import ohnosequences.typesets._
import ohnosequences.scarph.AnySealedVertexType


trait AnyVertexTypeWithId extends AnySealedVertexType{
  type Id <: Singleton with AnyProperty.ofValue[String]
  val id : Id
  val containId : Id ∈ Record#Properties
}

abstract class VertexTypeWithId[P <: Singleton with AnyProperty.ofValue[String],R <: AnyRecord](
  val id: P,
  val label: String,
  val record: R)
(
  implicit containId : P ∈ R#Properties
) extends AnyVertexTypeWithId{
  type Record = R
  type Id = P
}