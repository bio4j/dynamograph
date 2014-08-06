package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph.{AnySealedVertexType, VertexType, AnyEdgeType, AnyVertexType}
import com.bio4j.dynamograph.reader.GoReaders
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}

class DynamoDbDao extends AnyDynamoDbDao{

  def get[VT <: Singleton with AnySealedVertexType](id : String, vertexType : VT) : Either[String, vertexType.record.Rep] = GoReaders.reader(vertexType).read(id)

  def getInRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]] = GoReaders.edgeReaders.get(et).get.readIn(id)

  def getOutRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]] = GoReaders.edgeReaders.get(et).get.readOut(id)

}
