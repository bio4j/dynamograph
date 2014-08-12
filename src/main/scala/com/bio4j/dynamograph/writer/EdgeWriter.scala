package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoEdge}
import ohnosequences.typesets._
import ohnosequences.tabula._
import ohnosequences.scarph._
import ohnosequences.tabula.impl._
import ohnosequences.tabula.impl.actions._
import ohnosequences.tabula.ThroughputStatus
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.PutItemRequest



trait AnyEdgeWriter extends AnyWriter{
  type Element <: AnyDynamoEdge
}

class EdgeWriter[E <: AnyDynamoEdge, R <: AnyRegion]
  (val element: E, val edgeTables: EdgeTables[E, R]) extends AnyEdgeWriter{

  type Element = E


  def write(edge: Element#Rep): List[PutItemRequest] = {
    val inTableAttrs = Map(
     edgeTables.inTable.hashKey.label -> new AttributeValue().withS(getValue(edge, targetId.label)),
     edgeTables.inTable.rangeKey.label -> new AttributeValue().withS(getValue(edge, relationId.label))
    )
    val outTableAttrs = Map(
      edgeTables.outTable.hashKey.label -> new AttributeValue().withS(getValue(edge, sourceId.label)),
      edgeTables.outTable.rangeKey.label -> new AttributeValue().withS(getValue(edge, relationId.label))
    )

    val inTableRequest = new PutItemRequest().withTableName(edgeTables.inTable.name).withItem(inTableAttrs)
    val outTableRequest = new PutItemRequest().withTableName(edgeTables.outTable.name).withItem(outTableAttrs)
    val tableRequest = new PutItemRequest().withTableName(edgeTables.edgeTable.name).withItem(edge)
    
    return List(inTableRequest,outTableRequest, tableRequest)
  }

}

