package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.typesets._

trait AnyVertexTypeWithId  extends AnyVertexType{
  type Id <: Singleton with AnyProperty.ofValue[String]
  val id : Id
}

object VertexTypeWithId {
  /* Additional methods */
  implicit def vertexTypeOps[VT <: AnyVertexTypeWithId](vt: VT) = VertexTypeOps(vt)
  case class   VertexTypeOps[VT <: AnyVertexTypeWithId](val vt: VT) 
    extends HasPropertiesOps(vt) {}
}

class VertexTypeWithId[
  P <: Singleton with AnyProperty.ofValue[String]
](
  val id: P,
  val label: String
)extends AnyVertexTypeWithId{
  type Id = P
}