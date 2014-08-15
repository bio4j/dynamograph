package com.bio4j.dynamograph.dynamodb

import com.amazonaws.services.dynamodbv2.model._
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.amazonaws.{AmazonClientException, AmazonServiceException}
import java.util


class DynamoDbExecutor(val ddb: AmazonDynamoDB) extends AnyDynamoDbExecutor {


  override def execute(request: QueryRequest): List[Map[String, AttributeValue]] = withinTry {
    var result : QueryResult = ddb.query(request)
    val resultList = result.getItems
    while (result.getLastEvaluatedKey !=null){
      request.withExclusiveStartKey(result.getLastEvaluatedKey)
      result = ddb.query(request)
      resultList ++= result.getItems
    }
    resultList.map(x => x.asScala.toMap).toList
  }


  override def execute(request: BatchGetItemRequest): List[Map[String, AttributeValue]] = withinTry {
    var result = ddb.batchGetItem(request)
    var resultList = flattenResult(result)
    while (result.getUnprocessedKeys.size() > 0){
      request.withRequestItems(result.getUnprocessedKeys)
      result = ddb.batchGetItem(request)
      resultList ++= flattenResult(result)
    }
    resultList.map(x => x.asScala.toMap).toList
  }

  override def execute(request: GetItemRequest): Map[String, AttributeValue] = withinTry {
    ddb.getItem(request).getItem match {
      case null => Map()
      case item: java.util.Map[String,AttributeValue] => item.asScala.toMap
    }
  }

  override def execute(requests : List[PutItemRequest]) : Unit = withinTry {
    val writeRequestItems : java.util.Map[String,java.util.List[WriteRequest]] = new util.HashMap[String,java.util.List[WriteRequest]]()
    for (request <- requests){
      if (!writeRequestItems.containsKey(request.getTableName)){
        writeRequestItems.put(request.getTableName,new util.ArrayList[WriteRequest]())
      }
      writeRequestItems.get(request.getTableName).add(new WriteRequest().withPutRequest(new PutRequest().withItem(request.getItem)))
    }
    val request = new BatchWriteItemRequest().withRequestItems(writeRequestItems)
    var result = ddb.batchWriteItem(request)
    while (result.getUnprocessedItems.size() > 0){
      request.withRequestItems(result.getUnprocessedItems)
      result = ddb.batchWriteItem(request)
    }
  }

  private def flattenResult(batchResult : BatchGetItemResult) = for {
    values <- batchResult.getResponses.values()
    singleValue <- values
  } yield singleValue

  private def withinTry[A](f: => A): A = {
    try{
      f
    } catch {
      case ex @ (_ : ResourceNotFoundException | _ : ProvisionedThroughputExceededException | _ : InternalServerErrorException) => {
        //TODO: BA logging
        throw ex
      }
      case ex: AmazonServiceException => {
        //TODO: BA logging
        throw ex
      }
      case ex: AmazonClientException => {
        //TODO: BA logging
        throw ex
      }
    }
  }
}
