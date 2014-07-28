package com.bio4j.dynamograph

import ohnosequences.scarph.VertexType
import ohnosequences.typesets.TypeSet
import ohnosequences.tabula.{AnyItem, Item}


abstract class DynamoVertexType[As <:TypeSet ](override val label: String) extends VertexType(label){
  type Attributes = As
  val attributes : Attributes
}
