package com.bio4j.dynamograph.reader

import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.AnyDynamoEdge
import com.amazonaws.services.dynamodbv2.model._
import com.bio4j.dynamograph.model.GeneralSchema.{nodeId, id, relationId}
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._


abstract class EdgeReader[ET <: AnyDynamoEdge](val eType: ET, val edgeTables: EdgeTables[ET,AnyRegion],  val dbExecutor : AnyDynamoDbExecutor) extends AnyEdgeReader {
  type returnType = List[Map[String,AttributeValue]]
  type edgeType = ET

  def readOut(vId : id.Raw) : returnType = read(vId, edgeTables.outTable.name)

  def readIn(vId : id.Raw) : returnType = read(vId, edgeTables.inTable.name)

  private def read(vId : id.Raw, linkingTableName : String) = {
    val hashKeyCondition = new Condition()
      .withComparisonOperator(ComparisonOperator.EQ)
      .withAttributeValueList(new AttributeValue().withS(vId));
    val request = new QueryRequest().withTableName(linkingTableName).withKeyConditions(Map(nodeId.label -> hashKeyCondition))
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
