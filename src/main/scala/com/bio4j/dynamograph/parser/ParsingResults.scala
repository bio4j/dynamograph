package com.bio4j.dynamograph.parser


trait ParsingResults extends Traversable[SingleElement] {
  def foreach[U](f: SingleElement => U)
}

case class SingleElement(val vertexAttributes: Map[String,String], val edges: List[Map[String,String]])
