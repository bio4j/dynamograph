package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.GeneralSchema.{relationId, targetId, sourceId, id}
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.AnyDynamoEdge
import com.bio4j.dynamograph.model.go.GoSchema._


class GOMapper extends AnyMapper{
  //TODO: type of the right element in pair
  type ResultType = (GoTerm.Rep ,List[Any])

  override def map(element: SingleElement): GOMapper#ResultType = {
    val vertex = GoTerm ->> element.vertexAttributes.mapValues(mapValue)
    val vertexId : String = vertex.get(id)

    def toEdge(attributes: Map[String,String])  = {
      val targetIdentifier : String = attrValue(attributes, targetId.label)
      val rawEdge = Map(relationId.label -> (vertexId + targetIdentifier), sourceId.label -> vertexId, targetId.label -> targetIdentifier).
        mapValues(mapValue)
      createEdge(attrValue(attributes, ParsingContants.relationType), rawEdge)
    }

    val edges = element.edges.map(toEdge)
    (vertex, edges)
  }

  private def mapValue(x : String) : AttributeValue = new AttributeValue().withS(x)

  private def attrValue(attributes : Map[String, String], name : String) : String = attributes.get(name).get

  private def createEdge(relationType : String, rawEdge : Map[String, AttributeValue]) = relationType match {
    case relType if relType == IsAType.label => IsA ->> rawEdge
    case relType if relType == HasPartType.label => HasPart ->> rawEdge
    case relType if relType == PartOfType.label => PartOf ->> rawEdge
    case relType if relType == NegativelyRegulatesType.label => NegativelyRegulates ->> rawEdge
    case relType if relType == PositivelyRegulatesType.label => PositivelyRegulates ->> rawEdge
    case relType if relType == RegulatesType.label => Regulates ->> rawEdge
    case relType if relType == NamespaceType.label => GoNamespaces ->> rawEdge
  }

}
