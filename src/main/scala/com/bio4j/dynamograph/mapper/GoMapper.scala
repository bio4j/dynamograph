package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.Properties.{relationId, targetId, sourceId, id}
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import com.amazonaws.services.dynamodbv2.model.{PutItemRequest, AttributeValue}
import com.bio4j.dynamograph.writer.GoWriters
import ohnosequences.scarph.ops.default._
import com.bio4j.dynamograph.writer.AnyEdgeWriter
import com.bio4j.dynamograph.writer.AnyVertexWriter

class GoMapper(val vertexWriter : AnyVertexWriter,val edgeWriters : Map[String, AnyEdgeWriter]) extends AnyMapper{

  override def map(element: SingleElement): List[PutItemRequest] = {
    val vertex = GoTerm ->> element.vertexAttributes.mapValues(mapValue)
    val vertexId : String = vertex.get(id)

    def toWriteOperation(attributes: Map[String,String]) : List[PutItemRequest]   = {
      val targetIdentifier : String = attrValue(attributes, targetId.label)
      val rawEdge = (Map(
        relationId.label -> (vertexId + targetIdentifier), 
        sourceId.label   -> vertexId, 
        targetId.label   -> targetIdentifier
      ) ++ attributes.filterKeys(x => !x.equals(targetId.label) && !x.equals(ParsingContants.relationType))
      ).mapValues(mapValue)
      createEdge(attrValue(attributes, ParsingContants.relationType), rawEdge)
    }
    vertexWriter.write(vertex) ::: element.edges.map(toWriteOperation).flatten
  }

  private def mapValue(x : String) : AttributeValue = new AttributeValue().withS(x)

  private def attrValue(attributes : Map[String, String], name : String) : String = attributes.get(name).get

  private def createEdge(relationType: String, rawEdge: Map[String, AttributeValue]): List[PutItemRequest] =
    edgeWriters.get(relationType).fold[List[PutItemRequest]](Nil)(x => x.write(rawEdge))
}
