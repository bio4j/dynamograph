package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue

case class DynamoRawEdge(tableName: String,id: String,target: String,source: String,attributes : Map[String,AttributeValue]) {
  def getAttributeValue(name: String) = attributes.get(name).get.getS
}
