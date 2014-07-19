package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables
import ohnosequences.tabula.AnyRegion
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.AnyDynamoEdge


abstract class EdgeWriter[ET <:AnyDynamoEdge, R <: AnyRegion](val eType: ET, val edgeTables: EdgeTables[ET,R]) extends AnyEdgeWriter{
  type writeType = PutItemRequest
  type edgeType = ET

  def write(edge: edgeType#Rep) : List[writeType] = {
    val inTableAttrs = Map(edgeTables.inTable.hashKey.label -> new AttributeValue().withS(getValue(edge, targetId.label)),edgeTables.inTable.rangeKey.label -> new AttributeValue().withS(getValue(edge, relationId.label)))
    val outTableAttrs = Map(edgeTables.outTable.hashKey.label -> new AttributeValue().withS(getValue(edge, sourceId.label)),edgeTables.outTable.rangeKey.label -> new AttributeValue().withS(getValue(edge, relationId.label)))

    val inTableRequest = new PutItemRequest().withTableName(edgeTables.inTable.name).withItem(inTableAttrs)
    val outTableRequest = new PutItemRequest().withTableName(edgeTables.outTable.name).withItem(outTableAttrs)
    val tableRequest = new PutItemRequest().withTableName(edgeTables.edgeTable.name).withItem(edge)
    
    return List(inTableRequest,outTableRequest, tableRequest)
  }

  private def getValue(rep: edgeType#Rep, attributeName : String) : String = rep.get(attributeName).get.getS
}

