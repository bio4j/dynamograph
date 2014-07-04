package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model.{QueryRequest, BatchGetItemRequest, AttributeValue, GetItemRequest}

trait AnyDynamoDbExecutor {
  def execute(request : GetItemRequest) : Map[String, AttributeValue]

  def execute(request : BatchGetItemRequest) : List[Map[String, AttributeValue]]

  def execute(request : QueryRequest) : List[Map[String, AttributeValue]]
 }
