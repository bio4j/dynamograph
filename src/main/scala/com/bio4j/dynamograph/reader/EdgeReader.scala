package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.AnyEdgeTypeWithId
import com.bio4j.dynamograph.model._
import ohnosequences.tabula.AnyCompositeKeyTable
import com.amazonaws.services.dynamodbv2.model._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

trait AnyEdgeReader{
  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables : EdgeTables
  
  val dbExecutor : AnyDynamoDbExecutor
  
  def readOut(vId : EdgeTables#EdgeType#Id#Value) : List[Map[String,AttributeValue]] = read(vId, edgeTables.outTable)

  def readIn(vId : EdgeTables#EdgeType#Id#Value) : List[Map[String,AttributeValue]] = read(vId, edgeTables.inTable)

  private def read(vId : EdgeTables#EdgeType#Id#Value, table : AnyCompositeKeyTable) : List[Map[String,AttributeValue]] = {
    val hashKeyCondition = new Condition()
      .withComparisonOperator(ComparisonOperator.EQ)
      .withAttributeValueList(new AttributeValue().withS(vId));
    val request = new QueryRequest().withTableName(table.name).withKeyConditions(Map(table.hashKey.label -> hashKeyCondition))
    val linkingTableResults = dbExecutor.execute(request)

    val keys = for {
      r <- linkingTableResults
      key <- r.get(table.hashKey.label)
    } yield key.getS()

    val tableKeys = for (key <- keys) yield Map(edgeTables.edgeTable.hashKey.label -> new AttributeValue().withS(key)).asJava
    val batchRequest = new BatchGetItemRequest().withRequestItems(
      Map(edgeTables.edgeTable.name -> new KeysAndAttributes().withKeys(tableKeys)))
    dbExecutor.execute(batchRequest)
  }
}

object AnyEdgeReader{
  type withTableType[ET <: Singleton with AnyEdgeTables] = AnyEdgeReader{ type EdgeTables = ET}
}


abstract class EdgeReader[ET <: Singleton with AnyEdgeTables](val edgeTables : ET, override val dbExecutor : AnyDynamoDbExecutor) extends AnyEdgeReader {
  type EdgeTables = ET

}
