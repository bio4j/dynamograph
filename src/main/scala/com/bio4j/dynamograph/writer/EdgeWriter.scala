package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables
import ohnosequences.tabula.AnyRegion
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.model.GeneralSchema._


trait EdgeWriter extends AnyEdgeWriter{
  type writeType = PutItemRequest

  val edgeTables: EdgeTables[edgeType,AnyRegion]
  def write(e: edgeType#Rep) : List[writeType] = {
    val inTableAttrs = Map(edgeTables.inTable.hashKey.label -> new AttributeValue().withS(getValue(e, targetId.label)),edgeTables.inTable.rangeKey.label -> new AttributeValue().withS(getValue(e, relationId.label)))
    val outTableAttrs = Map(edgeTables.outTable.hashKey.label -> new AttributeValue().withS(getValue(e, sourceId.label)),edgeTables.outTable.rangeKey.label -> new AttributeValue().withS(getValue(e, relationId.label)))

    val inTableRequest = new PutItemRequest().withTableName(edgeTables.inTable.name).withItem(inTableAttrs)
    val outTableRequest = new PutItemRequest().withTableName(edgeTables.outTable.name).withItem(outTableAttrs)
    val tableRequest = new PutItemRequest().withTableName(edgeTables.edgeTable.name).withItem(e)
    
    return List(inTableRequest,outTableRequest, tableRequest)
  }

  private def getValue(rep: edgeType#Rep, attributeName : String) : String = rep.get(attributeName).get.getS
}

