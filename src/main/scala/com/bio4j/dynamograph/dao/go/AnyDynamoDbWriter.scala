package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model.AttributeValue

trait AnyDynamoDbWriter {
  def write(tableName : String, values: List[Map[String, AttributeValue]])
}
