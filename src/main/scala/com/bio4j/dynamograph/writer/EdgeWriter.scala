package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model._

import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoEdge}

import ohnosequences.typesets._

import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._

import ohnosequences.scarph._, ops.default._


trait AnyEdgeWriter extends AnyWriter {
  
  type Element <: AnyDynamoEdge
}

class EdgeWriter[E <: AnyDynamoEdge, R <: AnyRegion]
  (val element: E, val edgeTables: EdgeTables[E, R]) extends AnyEdgeWriter {

  type Element = E


  def write(rep: element.Rep): List[WriteType] = {
    val edgeRep = edgeTables.item fields (
        (relationId     ->> element.getValue(rep, relationId)) :~:
        (sourceId       ->> element.getValue(rep, sourceId)) :~:
        (targetId       ->> element.getValue(rep, targetId)) :~:
        ∅
      )

    val inTableRequest  = InCompositeKeyTable(edgeTables.inTable,   Active(edgeTables.inTable,    ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeTables.inItem  withValue (edgeTables.inItem fields ((edgeRep as edgeTables.inItem.record):edgeTables.inItem.record.Raw))
    val outTableRequest = InCompositeKeyTable(edgeTables.outTable,  Active(edgeTables.outTable,   ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeTables.outItem withValue (edgeTables.outItem fields ((edgeRep as edgeTables.outItem.record):edgeTables.outItem.record.Raw))
    val tableRequest    = InHashKeyTable(edgeTables.edgeTable, Active(edgeTables.edgeTable,  ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeTables.item    withValue  edgeRep


    List(inTableRequest, outTableRequest, tableRequest)
  }

}

