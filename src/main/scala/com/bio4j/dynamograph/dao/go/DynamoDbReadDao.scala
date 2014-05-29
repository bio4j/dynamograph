package com.bio4j.dynamograph.dao.go

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model._
import com.bio4j.dynamograph.dynamodb.DynamoDbMapper
import com.bio4j.dynamograph.dynamodb.Constants._
import com.bio4j.dynamograph.DynamoRawVertex
import com.bio4j.dynamograph.DynamoElementIdentifier
import com.bio4j.dynamograph.DynamoRawEdge
import scala.Some
import com.bio4j.dynamograph.dynamodb.DynamoDbEdgeModel
import scala.collection.mutable
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

class DynamoDbReadDao(val ddb : AmazonDynamoDB, val mapper : DynamoDbMapper) extends AnyDynamoDbReadDao{

  //TODO: BA Further improvements
  override def getOutRelationships(id: DynamoElementIdentifier, edgeModel: Option[DynamoDbEdgeModel]): List[DynamoRawEdge] = edgeModel match {
    case None => Nil
    case Some(t) => retrieveEdges(id, t.outEdgeTableName, t.edgeTableName)
  }

  override def getInRelationships(id: DynamoElementIdentifier, edgeModel: Option[DynamoDbEdgeModel]): List[DynamoRawEdge] = edgeModel match {
      case None => Nil
      case Some(t) => retrieveEdges(id, t.inEdgeTableName, t.edgeTableName)
  }

  override def get(identifier: DynamoElementIdentifier): Option[DynamoRawVertex] = {
    try {
      ddb.getItem(new GetItemRequest()
        .withTableName(identifier.tableName)
        .withKey(Map(nodeTableId -> new AttributeValue().withS(identifier.id)))
      ).getItem match {
        case null => None
        case item: java.util.Map[String,AttributeValue] => Option(mapper.mapToDynamoRawVertex(identifier.tableName, item.toMap))
      }
    } catch {
      //TODO: BA Empty catch!!!
      case t: Throwable => None
    }
  }

  private def retrieveEdges(id: DynamoElementIdentifier, linkingTableName: String, relationTableName: String) : List[DynamoRawEdge] = {
    val hashKeyCondition = new Condition()
      .withComparisonOperator(ComparisonOperator.EQ)
      .withAttributeValueList(new AttributeValue().withS(id.id));
    val results = queryDb(linkingTableName, Map(relationTableNodeId -> hashKeyCondition));
    val keys = for {
      r <- results
      key <- r.get(relationTableId)
    } yield key.getS()
    batchGet(relationTableName, keys)
  }

  private def batchGet(tableName: String, keys : List[String]) : List[DynamoRawEdge] = {
    val tableKeys : List[java.util.Map[String,AttributeValue]] = for (key <- keys) yield Map(relationTableId -> new AttributeValue().withS(key)).asJava
    val batchRequest = new BatchGetItemRequest().withRequestItems(
      Map(tableName -> new KeysAndAttributes().withKeys(tableKeys)))
    var result : BatchGetItemResult = ddb.batchGetItem(batchRequest)
    val resultList = mutable.MutableList[DynamoRawEdge]((for (edge <- result.getResponses.get(tableName)) yield mapper.mapToDynamoRawEdge(tableName, edge toMap)):_*)
    while (result.getUnprocessedKeys.size() > 0){
      batchRequest.withRequestItems(result.getUnprocessedKeys)
      result = ddb.batchGetItem(batchRequest)
      resultList ++= (for (edge <- result.getResponses.get(tableName)) yield mapper.mapToDynamoRawEdge(tableName, edge toMap))
    }
    resultList toList
  }

  private def queryDb(tableName: String, keyConditions: Map[String, Condition]) : List[Map[String, AttributeValue]] = {
    val queryRequest = new QueryRequest().withTableName(tableName).withKeyConditions(keyConditions)
    var result : QueryResult = ddb.query(queryRequest)
    val resultList = result.getItems
    while (result.getLastEvaluatedKey !=null){
      val nextQueryRequest = new QueryRequest().withTableName(tableName).withKeyConditions(keyConditions)
        .withExclusiveStartKey(result.getLastEvaluatedKey)
      result = ddb.query(nextQueryRequest)
      resultList ++= result.getItems
    }
    resultList.map(x => x.asScala.toMap).toList
  }

}
