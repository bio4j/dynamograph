package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.{ScalarAttributeType, AttributeDefinition}


case class DynamoDbEdgeModel(edgeName: String,
                             relationId: AttributeDefinition = new AttributeDefinition("relId",ScalarAttributeType.S),
                             nodeId: AttributeDefinition = new AttributeDefinition("id",ScalarAttributeType.S),
                             writeThroughput: Int = 1, readThroughput: Int = 1)
