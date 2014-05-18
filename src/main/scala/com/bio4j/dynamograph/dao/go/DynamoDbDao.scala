package com.bio4j.dynamograph.dao.go

import java.util.Map
import com.amazonaws.services.dynamodbv2.model.AttributeValue

trait DynamoDbDao {
  def get(id : String) : Map[String,AttributeValue]

  def getInRelationships(id : String) : List[Map[String,AttributeValue]]

  def getOutRelationships(id : String) : List[Map[String,AttributeValue]]

}
