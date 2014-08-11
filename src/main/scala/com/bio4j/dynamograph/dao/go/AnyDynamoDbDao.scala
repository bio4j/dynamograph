package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}
import ohnosequences.scarph.AnySealedVertexType

trait AnyDynamoDbDao {

  def getInRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]]

  def getOutRelationships(id : String, et : AnyDynamoEdge) : List[Map[String,AttributeValue]]

}
