package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.{ScalarAttributeType, AttributeDefinition}
import Constants._


case class DynamoDbNodeModel(nodeName: String, writeThroughput: Int = 1, readThroughput: Int = 1){
  val hash = new AttributeDefinition().withAttributeName(nodeTableId).withAttributeType(ScalarAttributeType.S)
}
