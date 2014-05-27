package com.bio4j.dynamograph.dao.go

import com.bio4j.dynamograph.{DynamoRawEdge, DynamoRawVertex}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.DynamoDbMapper

class DynamoDbDao(val ddb : AmazonDynamoDB, val mapper : DynamoDbMapper) extends IDynamoDbDao{

  override def getOutRelationships(id: String): List[DynamoRawEdge] = ???

  override def getInRelationships(id: String): List[DynamoRawEdge] = ???

  override def get(id: String): DynamoRawVertex = ???

}
