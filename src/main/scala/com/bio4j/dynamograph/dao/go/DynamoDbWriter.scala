package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.model._
import scala.collection.JavaConversions._
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB

class DynamoDbWriter(val ddb : AmazonDynamoDB) extends AnyDynamoDbWriter {

  //TODO: BA refactor solution
  override def write(tableName: String, values: List[Map[String, AttributeValue]]): Unit = {
    val writeOperations = new java.util.ArrayList[WriteRequest]()
    for (value <- values){
      writeOperations.add(new WriteRequest().withPutRequest(new PutRequest().withItem(value)))
    }
    var operations: java.util.Map[String, java.util.List[WriteRequest]] = Map(tableName -> writeOperations)
    do{
      try{
      val result = ddb.batchWriteItem(new BatchWriteItemRequest().withRequestItems(operations))
      operations = result.getUnprocessedItems
      }catch {
        case t: ProvisionedThroughputExceededException => ()
      }
    } while (!operations.isEmpty)
  }
}
