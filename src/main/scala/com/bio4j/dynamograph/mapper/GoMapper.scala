package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.GeneralSchema.{relationId, targetId, sourceId, id}
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import com.amazonaws.services.dynamodbv2.model.{PutItemRequest, AttributeValue}
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.writer.GoWriters
import ohnosequences.scarph.ops.default._


class GoMapper extends AnyMapper{

  override def map(element: SingleElement): List[PutItemRequest] = {
    val vertex = GoTerm ->> element.vertexAttributes.mapValues(mapValue)
    val vertexId : String = vertex.get(id)

    def toWriteOperation(attributes: Map[String,String]) : List[PutItemRequest]   = {
      val targetIdentifier : String = attrValue(attributes, targetId.label)
      val rawEdge = Map(relationId.label -> (vertexId + targetIdentifier), sourceId.label -> vertexId, targetId.label -> targetIdentifier).
        mapValues(mapValue)
      createEdge(attrValue(attributes, ParsingContants.relationType), rawEdge)
    }
    GoWriters.GoTermVertexWriter.write(vertex) ::: element.edges.map(toWriteOperation).flatten
  }

  private def mapValue(x : String) : AttributeValue = new AttributeValue().withS(x)

  private def attrValue(attributes : Map[String, String], name : String) : String = attributes.get(name).get

  private def createEdge(relationType : String, rawEdge : Map[String, AttributeValue]) : List[PutItemRequest] = relationType match {
    case relType if relType == IsAType.label => GoWriters.IsAEdgeWriter.write (IsA ->> rawEdge)
    case relType if relType == HasPartType.label => GoWriters.HasPartEdgeWriter.write (HasPart ->> rawEdge)
    case relType if relType == PartOfType.label => GoWriters.PartOfEdgeWriter.write (PartOf ->> rawEdge)
    case relType if relType == NegativelyRegulatesType.label => GoWriters.NegativelyRegulatesEdgeWriter.write (NegativelyRegulates ->> rawEdge)
    case relType if relType == PositivelyRegulatesType.label => GoWriters.PositivelyRegulatesEdgeWriter.write (PositivelyRegulates ->> rawEdge)
    case relType if relType == RegulatesType.label => GoWriters.RegulatesEdgeWriter.write (Regulates ->> rawEdge)
    case relType if relType == NamespaceType.label => GoWriters.NamespaceEdgeWriter.write (Namespace ->> rawEdge)
  }

}
