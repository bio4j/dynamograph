package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph.{AnySealedVertexType, VertexType, AnyEdgeType, AnyVertexType}
import com.bio4j.dynamograph.reader.GoReaders
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}
import com.bio4j.dynamograph.AnyVertexTypeWithId

class DynamoDbDao extends AnyDynamoDbDao {


  // TODO why not use a reader of the corresponding type here?
  def get[VT <: Singleton with AnyVertexTypeWithId](id : String, vertexType : VT): Either[String, VT#Record#Rep] = {

      GoReaders.reader(vertexType).read(id)

    } 

  def getInRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]] = GoReaders.edgeReaders.get(et).get.readIn(id)

  def getOutRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]] = GoReaders.edgeReaders.get(et).get.readOut(id)

}
