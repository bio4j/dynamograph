package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables
import ohnosequences.tabula.AnyRegion
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.AnyDynamoEdge

trait AnyEdgeWriter extends AnyWriter{
  type Element <: AnyDynamoEdge
}

class EdgeWriter[E <: AnyDynamoEdge, R <: AnyRegion]
  (val element: E, val edgeTables: EdgeTables[E,R]) extends AnyEdgeWriter{

  type Element = E

  def write(rep: element.Rep): List[WriteType] = {
    val inTableAttrs = Map(
      edgeTables.inTable.hashKey.label  -> new AttributeValue().withS(element.getValue(rep, targetId.label)),
      edgeTables.inTable.rangeKey.label -> new AttributeValue().withS(element.getValue(rep, relationId.label))
    )
    val outTableAttrs = Map(
      edgeTables.outTable.hashKey.label  -> new AttributeValue().withS(element.getValue(rep, sourceId.label)),
      edgeTables.outTable.rangeKey.label -> new AttributeValue().withS(element.getValue(rep, relationId.label))
    )

    val inTableRequest  = new PutItemRequest().withTableName(edgeTables.inTable.name).withItem(inTableAttrs)
    val outTableRequest = new PutItemRequest().withTableName(edgeTables.outTable.name).withItem(outTableAttrs)
    val tableRequest    = new PutItemRequest().withTableName(edgeTables.edgeTable.name).withItem(rep)
    
    List(inTableRequest, outTableRequest, tableRequest)
  }

  // NOTE: we don't need it here, because we have it in the `AnyDynamoEdge` type
  // private def getValue(rep: edge.Rep, attributeName: String) : String = rep.get(attributeName).get.getS
}

