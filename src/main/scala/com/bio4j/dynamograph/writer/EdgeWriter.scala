package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.AnyDynamoEdge
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.model.go.TableGOImplementation._


trait EdgeWriter[E <:AnyDynamoEdge] extends AnyEdgeWriter[E]{
  type writeType = PutItemRequest

  val edgeTables: EdgeTables[E,AnyRegion];
  def write(e: E#Rep) : List[writeType] = {
    val inTableAttrs = Map(edgeTables.inTable.key.hash.label -> new AttributeValue().withS(e.target),edgeTables.inTable.key.range.label -> new AttributeValue().withS(e.id))
    val outTableAttrs = Map(edgeTables.outTable.key.hash.label -> new AttributeValue().withS(e.source),edgeTables.outTable.key.range.label -> new AttributeValue().withS(e.id))
    val tableAttrs = e.attributes ++ Map(edgeTables.edgeTable.key.hash.label -> new AttributeValue().withS(e.id))
    
    val inTableRequest = new PutItemRequest().withTableName(edgeTables.inTable.name).withItem(inTableAttrs)
    val outTableRequest = new PutItemRequest().withTableName(edgeTables.outTable.name).withItem(outTableAttrs)
    val tableRequest = new PutItemRequest().withTableName(edgeTables.edgeTable.name).withItem(tableAttrs)
    
    return List(inTableRequest,outTableRequest, tableRequest)
  }
}

