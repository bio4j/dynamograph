package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.model._

import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoEdge}
import ohnosequences.typesets.AnyTag._
import ohnosequences.tabula.AnyItem._

import ohnosequences.typesets._

import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._

import ohnosequences.scarph._, ops.default._


trait AnyEdgeWriter { edgeWriter =>

  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables: EdgeTables

  type EdgeTable = edgeTables.EdgeTable
  val edgeTable: EdgeTable = edgeTables.edgeTable
  type EdgeRecord = edgeTables.edgeType.Record;
  val edgeRecord : EdgeRecord = edgeTables.edgeType.record
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

  def write(edgeItemValue: TaggedWith[InItem])(implicit transf: From.Item[EdgeItem, SDKRep]): List[AnyPutItemAction] = {
    val inRep = inItem fields (
        (inTable.hashKey  ->> edgeItemValue.get(sourceId)) :~:
        (inTable.rangeKey ->> edgeItemValue.get(edgeTables.edgeId)) :~:
        ∅
      )

    val outRep = outItem  fields (
        (outTable.hashKey  ->> edgeItemValue.get(targetId)) :~:
        (outTable.rangeKey ->> edgeItemValue.get(edgeTables.edgeId)) :~:
        ∅
    )

    val inTableRequest  = InCompositeKeyTable(inTable,   Active(inTable,    ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem inItem  withValue inRep
    val outTableRequest = InCompositeKeyTable(outTable,  Active(outTable,   ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem outItem withValue outRep
    val tableRequest    = InHashKeyTable(edgeTable, Active(edgeTable,  ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeItem    withValue  edgeItemValue

    List(inTableRequest, outTableRequest, tableRequest)
  }
}

class EdgeWriter[ET <: AnyEdgeTables](val edgeTables: ET) extends AnyEdgeWriter {

  type EdgeTables = ET

}

