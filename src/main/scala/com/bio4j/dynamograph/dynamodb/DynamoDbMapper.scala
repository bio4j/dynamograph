package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.{DynamoElementIdentifier, DynamoRawEdge, DynamoRawVertex}


class DynamoDbMapper {
  // TODO: BA provide proper implementation
  def mapToDynamoRawVertex(values: Map[String, AttributeValue]) : DynamoRawVertex = {
    val dummyResult = DynamoRawVertex(DynamoElementIdentifier("id","test"),values);
    dummyResult
  }

  def mapToDynamoRawEdge(values: Map[String, AttributeValue]) : DynamoRawEdge = {
    val dummyResult = DynamoRawEdge(DynamoElementIdentifier("id","test"),DynamoElementIdentifier("id","target"),DynamoElementIdentifier("id","source"), values);
    dummyResult
  }

}
