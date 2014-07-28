package com.bio4j.dynamograph

import ohnosequences.scarph.VertexType
import ohnosequences.typesets.TypeSet
import ohnosequences.tabula.{AnyItem, Item}


class DynamoVertexType[As <:TypeSet ](override val label: String, val attributes : As) extends VertexType(label){
  type Attributes = As
}
