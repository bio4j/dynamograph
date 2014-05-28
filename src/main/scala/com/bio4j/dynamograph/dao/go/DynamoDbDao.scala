package com.bio4j.dynamograph.dao.go

import com.bio4j.dynamograph.{DynamoElementIdentifier, DynamoRawEdge, DynamoRawVertex}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.DynamoDbMapper
import com.bio4j.dynamograph.dynamodb.Constants._

class DynamoDbDao(val ddb : AmazonDynamoDB, val mapper : DynamoDbMapper) extends AnyDynamoDbDao{

  override def getOutRelationships(id: DynamoElementIdentifier): List[DynamoRawEdge] = ???

  override def getInRelationships(id: DynamoElementIdentifier): List[DynamoRawEdge] = ???

  override def get(identifier: DynamoElementIdentifier): Option[DynamoRawVertex] = {
    try {
      ddb.getItem(new GetItemRequest()
        .withTableName(identifier.tableName)
        .withKey(Map(nodeTableId -> new AttributeValue().withS(identifier.id)))
      ).getItem match {
        case null => None
        case item: java.util.Map[String,AttributeValue] => Option(mapper.mapToDynamoRawVertex(identifier.tableName, item.toMap))
      }
    } catch {
      case t: Throwable => None
    }
  }

}
