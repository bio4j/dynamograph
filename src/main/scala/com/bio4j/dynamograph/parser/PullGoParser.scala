package com.bio4j.dynamograph.parser

import scala.xml.pull._
import scala.io.Source


class PullGoParser(val src: Source) extends AnyGoParser {
  // simple values/properties
  val idTag = "id"
  val comment = "comment"
  val nameTag = "label"
  val definitionTag = "IAO_0000115"
  val namespaceTag = "hasOBONamespace"

  val partOf = ("partOf", "http://purl.obolibrary.org/obo/BFO_0000050")
  val hasPart = ("hasPart","http://purl.obolibrary.org/obo/BFO_0000051")
  val regulates = ("regulates","http://purl.obolibrary.org/obo/RO_0002211")
  val negativelyRegulates = ("negativelyRegulates","http://purl.obolibrary.org/obo/RO_0002212")
  val positivelyRegulates = ("positivelyRegulates","http://purl.obolibrary.org/obo/RO_0002213")

  // relationships properties
  val is_a = "is_a"
  val resource = "rdf:resource"

  val mapping = Map(idTag -> "id", nameTag -> "name", namespaceTag -> "namespace", definitionTag -> "definition",  comment -> "comment")
  val relationMapping = Map(partOf._2 -> partOf._1,hasPart._2 -> hasPart._1,regulates._2 -> regulates._1,
    negativelyRegulates._2 -> negativelyRegulates._1,positivelyRegulates._2 -> positivelyRegulates._1)

  override def foreach[U](f: SingleElement => U) = {
    val reader = new XMLEventReader(src)
    while(reader.hasNext){
      reader.next match {
        case EvElemStart(pre, "Class", _, _) => {
          f(parseSingleElement(reader))
        }
        case _ =>
      }
    }
  }

  private def parseSingleElement(parser: XMLEventReader) : SingleElement = {
    var done = false
    var vertex : Map[String, String] = Map()
    var edges : List[Map[String, String]] = List()
    //var singleGO : List[(String, String)] = List()
    while (parser.hasNext && !done){
      parser.next match {
        case EvElemEnd(_, "Class") => done = true
        case EvElemStart(pre, "Class", attrs, _) => skip("Class", parser)
        case EvElemStart(pre, "subClassOf", attrs, _) => edges ::= parseSingleRelation(attrs, parser)
        case EvElemStart(pre, label, _, _) if mapping.contains(label) => vertex += parseSingleProperty(label, parser)
        case _ => ()
      }
    }
    SingleElement(vertex, edges)
  }

  private def skip(label: String,parser: XMLEventReader) = {
    var done = false
    while (parser.hasNext && !done){
      parser.next match {
        case EvElemEnd(_, "Class") => done = true
        case _ =>
      }
    }
  }

  private def parseSingleProperty(label : String, parser: XMLEventReader) : (String, String) = {
    var done = false
    var value : String = null
    while (parser.hasNext && !done){
      parser.next match {
        case EvText(text) => value = text
        case EvElemEnd(_, endLabel) if label == endLabel => done = true
        case _ =>
      }
    }
    (mapping.getOrElse(label,""), value)
  }

  private def parseSingleRelation(attrs : scala.xml.MetaData,parser: XMLEventReader) : Map[String, String] =
    getAttributeValue(attrs, resource) match {
      case Some(StringPrefixMatcher(id)) => Map(is_a -> id)
      case _ => parseCompoundRelation(parser)
    }

  private def parseCompoundRelation(parser: XMLEventReader) : Map[String, String] = {
    var done = false
    var id : String = null
    var value : String = null
    while (parser.hasNext && !done){
      parser.next match {
        case EvElemEnd(_, "subClassOf") => done = true
        case EvElemStart(pre, "onProperty", attrs, _) => id = relationMapping.get(getAttributeValue(attrs, resource).get).get
        case EvElemStart(pre, "someValuesFrom", attrs, _) => value = StringPrefixMatcher(getAttributeValue(attrs, resource))
        case _ =>
      }
    }
    Map(id -> value)
  }

  private def getAttributeValue(attrs : scala.xml.MetaData, attrName : String) : Option[String] = {
    for (rValue <- attrs.asAttrMap.get(resource)) yield rValue
  }

  private object StringPrefixMatcher{
    def unapply(str:String):Option[String]= {
      str match {
        case s if s.startsWith("http://purl.obolibrary.org/obo/") => Some(s.stripPrefix("http://purl.obolibrary.org/obo/").replace('_',':'))
        case _ => None
      }
    }

    def apply(str:Option[String]):String= {
      str match {
        case Some(s) if s.startsWith("http://purl.obolibrary.org/obo/") => s.stripPrefix("http://purl.obolibrary.org/obo/").replace('_',':')
        case _ => null
      }
    }
  }
}


