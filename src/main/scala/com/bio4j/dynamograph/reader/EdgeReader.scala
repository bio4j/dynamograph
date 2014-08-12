package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model._
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.AnyDynamoEdge
import com.amazonaws.services.dynamodbv2.model._
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import ohnosequences.typesets.TypeSet
import com.bio4j.dynamograph.ServiceProvider

trait AnyEdgeReader{
  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables : EdgeTables
  
  val dbExecutor : AnyDynamoDbExecutor = ServiceProvider.dynamoDbExecutor 
  
  def readOut(vId : id.Value) : List[Map[String,AttributeValue]] = read(vId, edgeTables.outTable.name)

  def readIn(vId : id.Value) : List[Map[String,AttributeValue]] = read(vId, edgeTables.inTable.name)

  private def read(vId : id.Raw, linkingTableName : String) : List[Map[String,AttributeValue]] = {
    val hashKeyCondition = new Condition()
      .withComparisonOperator(ComparisonOperator.EQ)
      .withAttributeValueList(new AttributeValue().withS(vId));
    val request = new QueryRequest().withTableName(linkingTableName).withKeyConditions(Map(relationId.label -> hashKeyCondition))
    val linkingTableResults = dbExecutor.execute(request)

    val keys = for {
      r <- linkingTableResults
      key <- r.get(relationId.label)
    } yield key.getS()

    val tableKeys = for (key <- keys) yield Map(relationId.label -> new AttributeValue().withS(key)).asJava
    val batchRequest = new BatchGetItemRequest().withRequestItems(
      Map(edgeTables.edgeTable.name -> new KeysAndAttributes().withKeys(tableKeys)))
    dbExecutor.execute(batchRequest)
  }
}


abstract class EdgeReader[ET <: Singleton with AnyEdgeTables](val edgeTables : ET) extends AnyEdgeReader {
  type EdgeTables = ET

}
