package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoEdge}
import ohnosequences.typesets._
import ohnosequences.scarph._
import ohnosequences.tabula.ThroughputStatus
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.AnyEdgeTables
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._



trait AnyEdgeWriter extends AnyWriter{
  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables : EdgeTables
  
  def write(edge: Map[String,AttributeValue]): List[PutItemRequest] = {
    val inTableAttrs = Map(
     edgeTables.inTable.hashKey.label -> edge(edgeTables.edgeType.targetId.label),
     edgeTables.inTable.rangeKey.label -> edge(edgeTables.edgeType.id.label)
    )
    val outTableAttrs = Map(
      edgeTables.outTable.hashKey.label -> edge(edgeTables.edgeType.sourceId.label),
      edgeTables.outTable.rangeKey.label -> edge(edgeTables.edgeType.id.label)
    )

    val inTableRequest = new PutItemRequest().withTableName(edgeTables.inTable.name).withItem(inTableAttrs)
    val outTableRequest = new PutItemRequest().withTableName(edgeTables.outTable.name).withItem(outTableAttrs)
    val tableRequest = new PutItemRequest().withTableName(edgeTables.edgeTable.name).withItem(edge)
    
    return List(inTableRequest,outTableRequest, tableRequest)
  }
}

class EdgeWriter[ET <: Singleton with AnyEdgeTables](val edgeTables: ET) extends AnyEdgeWriter{

  type EdgeTables = ET

}

