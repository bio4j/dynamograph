package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.{ScalarAttributeType, AttributeDefinition}


case class DynamoDbNodeModel(nodeName: String,
                             hash: AttributeDefinition = new AttributeDefinition("id",ScalarAttributeType.S),
                             range:Option[AttributeDefinition] = None,
                             writeThroughput: Int = 1, readThroughput: Int = 1)
