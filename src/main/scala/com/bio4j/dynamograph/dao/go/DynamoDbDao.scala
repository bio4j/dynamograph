package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph.{VertexType, AnyEdgeType, AnyVertexType}
import com.bio4j.dynamograph.reader.GoReaders
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}

class DynamoDbDao extends AnyDynamoDbDao{

  def get(id : String, vt : AnyDynamoVertex) : Map[String,AttributeValue] = GoReaders.vertexReader(vt).get.read(id)

  def getInRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]] = GoReaders.edgeReader(et).get.readIn(id)

  def getOutRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]] = GoReaders.edgeReader(et).get.readOut(id)

}
