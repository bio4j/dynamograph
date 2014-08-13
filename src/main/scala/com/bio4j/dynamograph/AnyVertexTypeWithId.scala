package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.typesets._

trait AnyVertexTypeWithId  extends AnyVertexType{
  type Id <: Singleton with AnyProperty.ofValue[String]
  val id : Id
}

abstract class VertexTypeWithId[
  P <: Singleton with AnyProperty.ofValue[String]
](
  val id: P,
  val label: String
)extends AnyVertexTypeWithId{
  type Id = P
}