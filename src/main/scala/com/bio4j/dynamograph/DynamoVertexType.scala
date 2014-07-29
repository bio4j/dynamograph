package com.bio4j.dynamograph

import ohnosequences.scarph.VertexType
import ohnosequences.typesets.{AnyRecord, TypeSet}
import ohnosequences.tabula.{AnyItem, Item}


abstract class DynamoVertexType(override val label: String) extends VertexType(label){
  type VertexRecord <: AnyRecord
  val record : VertexRecord
}
