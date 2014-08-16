package com.bio4j.dynamograph.parser.go

import com.bio4j.dynamograph.model.Properties.name
import com.bio4j.dynamograph.model.go.GoSchema.GoNamespacesType
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}

import scala.io.Source

class PullGoNamespaceParser(val src: Source) extends AnyGoParser{
    override def foreach[U](f: SingleElement => U) = {
    	for (line <- src.getLines())
    	  f(new SingleElement(Map(name.label -> line.trim, ParsingContants.vertexType -> GoNamespacesType.label),Nil))
  }

}