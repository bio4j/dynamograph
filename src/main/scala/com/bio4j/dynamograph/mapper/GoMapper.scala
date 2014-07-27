package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.GeneralSchema.{relationId, targetId, sourceId, id}
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import com.amazonaws.services.dynamodbv2.model.{PutItemRequest, AttributeValue}
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.writer.GoWriters
import ohnosequences.scarph.ops.default._
import ohnosequences.tabula.AnyPutItemAction


class GoMapper extends AnyMapper{

  override def map(element: SingleElement): List[AnyPutItemAction] = {
    val vertex = GoTerm ->> element.vertexAttributes.mapValues(mapValue)
    val vertexId : String = vertex.get(id)

    def toWriteOperation(attributes: Map[String,String]) : List[AnyPutItemAction]   = {
      val targetIdentifier : String = attrValue(attributes, targetId.label)
      val rawEdge = Map(
        relationId.label -> (vertexId + targetIdentifier), 
        sourceId.label   -> vertexId, 
        targetId.label   -> targetIdentifier
      ).mapValues(mapValue)
      createEdge(attrValue(attributes, ParsingContants.relationType), rawEdge)
    }
    GoWriters.goTermVertexWriter.write(vertex) ::: element.edges.map(toWriteOperation).flatten
  }

  private def mapValue(x : String) : AttributeValue = new AttributeValue().withS(x)

  // NOTE: I don't get smth here, but this `.get` doesn't look nice in general
  private def attrValue(attributes : Map[String, String], name : String) : String = attributes.get(name).get

  // NOTE: the general problem that appears here is that we want to _map a value to a type_.
  // i.e. you want to get a type depending on a value. and generally there is no good solution for this in Scala.
  private def createEdge(relationType: String, rawEdge: Map[String, AttributeValue]): List[AnyPutItemAction] = {
    GoWriters.edgeWritersMap.get(relationType) match {
      case None => List() // no writer were found for this label
      case Some(writer) => writer.write(writer.element ->> rawEdge)
    }
  }

}
