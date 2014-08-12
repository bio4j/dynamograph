package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.{AnyDynamoEdge, AnyDynamoVertex}
import ohnosequences.scarph.{AnyEdgeType, AnyVertexType}

trait AnyDynamoDbDao {

  def get(id : String, vt : AnyVertexType) : Map[String,AttributeValue]

  def getInRelationships(id : String, et : AnyEdgeType) : List[Map[String,AttributeValue]]

  def getOutRelationships(id : String, et : AnyEdgeType) : List[Map[String,AttributeValue]]

}
