package com.bio4j.dynamograph.parser

import scala.xml.pull._
import scala.io.Source


object PullGoParser extends AnyGoParser {
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

  override def parse(src: Source): Map[String, List[(String, Any)]] = {
    var result : Map[String, List[(String, Any)]] = Map()
    val reader = new XMLEventReader(src)
    while(reader.hasNext){
      reader.next match {
        case EvElemStart(pre, "Class", _, _) => {
          result += parseSingleElement(reader)
        }
        case _ =>
      }
    }
    result
  }

  private def parseSingleElement(parser: XMLEventReader) : (String,List[(String, Any)]) = {
    var done = false
    var singleGO : List[(String, Any)] = List()
    while (parser.hasNext && !done){
      parser.next match {
        case EvElemEnd(_, "Class") => done = true
        case EvElemStart(pre, "Class", attrs, _) => skip("Class", parser)
        case EvElemStart(pre, "subClassOf", attrs, _) => singleGO ::= parseSingleRelation(attrs, parser)
        case EvElemStart(pre, label, _, _) if mapping.contains(label) => singleGO ::= parseSingleProperty(label, parser)
        case _ => ()
      }
    }
    (findSingleElementId(singleGO), singleGO)
  }

  private def findSingleElementId(attributes : List[(String, Any)]) : String = attributes.filter(p => p._1.equals("id")).head._2.asInstanceOf[String]


  private def skip(label: String,parser: XMLEventReader) = {
    var done = false
    while (parser.hasNext && !done){
      parser.next match {
        case EvElemEnd(_, "Class") => done = true
        case _ =>
      }
    }
  }

  private def parseSingleProperty(label : String, parser: XMLEventReader) : (String, Any) = {
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

  private def parseSingleRelation(attrs : scala.xml.MetaData,parser: XMLEventReader) : (String, Any) =
    getAttributeValue(attrs, resource) match {
      case Some(StringPrefixMatcher(id)) => (is_a, id)
      case _ => parseCompoundRelation(parser)
    }

  private def parseCompoundRelation(parser: XMLEventReader) : (String, Any) = {
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
    (id, value)
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


