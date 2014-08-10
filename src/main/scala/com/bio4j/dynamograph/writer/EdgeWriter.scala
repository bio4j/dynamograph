package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.model._
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoEdge}
import ohnosequences.typesets.AnyTag._
import ohnosequences.tabula.AnyItem._
import ohnosequences.typesets._
import ohnosequences.typesets.AnyRecord._
import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._
import ohnosequences.scarph._, ops.default._
import com.bio4j.dynamograph.AnyVertexTypeWithId


trait AnyEdgeWriter { edgeWriter =>

  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables: EdgeTables
  type EdgeType = edgeTables.EdgeType
  val edgeType : EdgeType = edgeTables.edgeType

  type EdgeTable = edgeTables.EdgeTable
  val edgeTable: EdgeTable = edgeTables.edgeTable
  type EdgeRecord = edgeTables.EdgeRecord;
  val edgeRecord : EdgeRecord = edgeTables.edgeRecord
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
  type InRecord = edgeTables.InRecord
  val inRecord: InRecord = edgeTables.inRecord 
  
  type EdgeId = edgeTables.EdgeId
  val edgeId : EdgeId = edgeTables.edgeId
  type SourceId = edgeTables.SourceId
  val srcId : SourceId = edgeTables.srcId
  type TargetId = edgeTables.TargetId
  val tgtId : TargetId = edgeTables.tgtId
  
  type InVertexId = edgeType.targetType.Id
  val inVertexId : InVertexId = edgeType.targetType.id
  
  implicit val containId : EdgeId ∈ EdgeRecord#Properties = edgeTables.containEdgeId
  implicit val edgeIdLookup : Lookup[EdgeRecord#Raw, edgeId.Rep] = edgeTables.edgeIdLookup
  
  implicit val containTargetId : TargetId ∈ EdgeRecord#Properties = edgeTables.containTargetId
  implicit val targetIdLookup : Lookup[EdgeRecord#Raw, tgtId.Rep] = edgeTables.targetIdLookup
  
  implicit val containSourceId : SourceId ∈ EdgeRecord#Properties  = edgeTables.containSourceId
  implicit val sourceIdLookup : Lookup[EdgeRecord#Raw, srcId.Rep] = edgeTables.sourceIdLookup
  
  
  
  def write(edgeItemValue: TaggedWith[EdgeRecord])(implicit transf: From.Item[EdgeItem, SDKRep]): List[AnyPutItemAction] = {
    val inRep = inItem ->> ( 
        inRecord ->> (
        (inVertexId ->> edgeItemValue.get(srcId)) :~:
        (edgeId ->> edgeItemValue.get(edgeId)) :~:
        ∅)
      )

    val outRep = outItem  fields (
        (outTable.hashKey  is edgeItemValue.get(tgtId)) :~:
        (outTable.rangeKey is edgeItemValue.get(edgeTables.edgeId)) :~:
        ∅
    )

    val inTableRequest  = InCompositeKeyTable(inTable,   Active(inTable,    ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem inItem  withValue inRep
    val outTableRequest = InCompositeKeyTable(outTable,  Active(outTable,   ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem outItem withValue  outItem ->> outRep.fields
    val tableRequest    = InHashKeyTable(edgeTable, Active(edgeTable,  ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeItem    withValue (edgeItem ->> edgeItemValue)

    List(inTableRequest, outTableRequest, tableRequest)
  }
}

class EdgeWriter[ET <: AnyEdgeTables](val edgeTables: ET) extends AnyEdgeWriter {

  type EdgeTables = ET

}

