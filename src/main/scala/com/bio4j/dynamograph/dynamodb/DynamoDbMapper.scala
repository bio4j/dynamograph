package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.{DynamoElementIdentifier, DynamoRawEdge, DynamoRawVertex}
import com.bio4j.dynamograph.dynamodb.Constants._


class DynamoDbMapper {

  def mapToDynamoRawVertex(tableName: String, values: Map[String, AttributeValue]) : DynamoRawVertex = {
    DynamoRawVertex(DynamoElementIdentifier(tableName,values.get(nodeTableId).get.getS),values);
  }

  // TODO: BA provide proper implementation
  def mapToDynamoRawEdge(tableName: String, values: Map[String, AttributeValue]) : DynamoRawEdge = {
    val dummyResult = DynamoRawEdge(DynamoElementIdentifier("id","test"),DynamoElementIdentifier("id","target"),DynamoElementIdentifier("id","source"), values);
    dummyResult
  }

}
