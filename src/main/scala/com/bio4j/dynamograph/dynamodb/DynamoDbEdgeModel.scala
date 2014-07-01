package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.{ScalarAttributeType, AttributeDefinition}
import Constants._


case class DynamoDbEdgeModel(edgeTableName: String, writeThroughput: Int = 1, readThroughput: Int = 1){
  val nodeId = new AttributeDefinition().withAttributeName(relationTableNodeId).withAttributeType(ScalarAttributeType.S)
  val relationId = new AttributeDefinition().withAttributeName(relationTableId).withAttributeType(ScalarAttributeType.S)
  val inEdgeTableName = s"in${edgeTableName}"
  val outEdgeTableName = s"out${edgeTableName}"
}