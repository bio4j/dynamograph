package com.bio4j.dynamograph.parser

import scala.io.Source

class TaxonomyParser(val src: Source) extends AnyGoParser {

  override def foreach[U](f: (SingleElement) => U): Unit = {
    for (line <- src.getLines()){
        f(processLine(line))
    }
  }

  private def processLine(line : String) : SingleElement = {

  }
}
