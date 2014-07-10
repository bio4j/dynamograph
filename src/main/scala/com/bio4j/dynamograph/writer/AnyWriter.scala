package com.bio4j.dynamograph.writer

import ohnosequences.scarph._
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}
import com.amazonaws.services.dynamodbv2.model.PutItemRequest


trait AnyWriter {
  // NOTE: maybe you had this unset here on purpose, I don't know
  type WriteType = PutItemRequest

  // NOTE: First I would unify vertex and edge writers more, because they really differ only 
  // in the upper bound for the "element type" (vertex or edge)
  // then, if we will have a val of the edge type anyway, it's better to have it here
  // and use for precise typing (in the `write` method).
  // our convention here is to name the type member same as the value, but with a capital letter
  type Element <: Representable
  val  element: Element

  // NOTE: for type projections it's always better (and most of the time essential) to use values instead of types:
  // i.e. if `element: Element`, then it's better to write `element.Rep` instead of `Element#Rep`.
  def write(rep: element.Rep): List[WriteType]
}

// NOTE: I moved AnyVertex/Edge writer trait to the corresponding classes (I would even put all this in one file, but it's not important)
