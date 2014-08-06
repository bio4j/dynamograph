package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.model._

import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoEdge}

import ohnosequences.typesets._

import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._

import ohnosequences.scarph._, ops.default._


trait AnyEdgeWriter { edgeWriter =>

  type DynamoEdge <: Singleton with AnyDynamoEdge
  val dynamoEdge : DynamoEdge

  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables: EdgeTables

  type EdgeTable = edgeTables.EdgeTable
  val edgeTable: EdgeTable = edgeTables.edgeTable
  type EdgeItem = edgeTables.EdgeItem
  val edgeItem: EdgeItem = edgeTables.edgeItem

  type OutTable = edgeTables.OutTable
  val outTable: OutTable = edgeTables.outTable
  type OutItem = edgeTables.OutItem
  val outItem: OutItem = edgeTables.outItem

  type InTable = edgeTables.InTable
  val inTable: InTable = edgeTables.inTable
  type InItem = edgeTables.InItem
  val inItem: InItem = edgeTables.inItem

  def write(rep: dynamoEdge.Rep)(implicit transf: From.Item[EdgeItem, SDKRep]): List[AnyPutItemAction] = {
    val edgeRep = edgeItem fields (
        (relationId       ->> dynamoEdge.getValue(rep, relationId)) :~:
        (sourceId       ->> dynamoEdge.getValue(rep, sourceId)) :~:
        (targetId       ->> dynamoEdge.getValue(rep, targetId)) :~:
        ∅
      )

    val inTableRequest  = InCompositeKeyTable(inTable,   Active(inTable,    ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem inItem  withValue (inItem fields ((edgeRep as inItem.record):inItem.record.Raw))
    val outTableRequest = InCompositeKeyTable(outTable,  Active(outTable,   ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem outItem withValue (outItem fields ((edgeRep as outItem.record):outItem.record.Raw))
    val tableRequest    = InHashKeyTable(edgeTable, Active(edgeTable,  ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeItem    withValue  edgeRep

    List(inTableRequest, outTableRequest, tableRequest)
  }
}

class EdgeWriter[E <: AnyDynamoEdge, ET <: AnyEdgeTables](val dynamoEdge : E, val edgeTables: ET) extends AnyEdgeWriter {

  type DynamoEdge = E
  type EdgeTables = ET




}

