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

  type EdgeTables <: AnyEdgeTables
  val edgeTables: EdgeTables
  
  
  type EdgeType = EdgeTables#EdgeType
  val edgeType : EdgeType = edgeTables.edgeType
  //
  type TargetId = EdgeTables#TargetId
  val targetId: TargetId = edgeTables.targetId
  type SourceId = EdgeTables#SourceId
  val sourceId: SourceId= edgeTables.sourceId

  type EdgeTable = EdgeTables#EdgeTable
  val edgeTable: EdgeTable = edgeTables.edgeTable
  type EdgeRecord = EdgeTables#EdgeRecord;
  val edgeRecord : EdgeRecord = edgeTables.edgeRecord
  type EdgeItem = EdgeTables#EdgeItem
  val edgeItem: EdgeItem = edgeTables.edgeItem

//  type OutTable = EdgeTables#OutTable
//  val outTable: OutTable = edgeTables.outTable
//  type OutItem = EdgeTables#OutItem
//  val outItem: OutItem = edgeTables.outItem

//  type InTable = EdgeTables#InTable
//  val inTable: InTable = edgeTables.inTable
//  type InItem = EdgeTables#InItem
//  val inItem: InItem = edgeTables.inItem

  type InRecord = edgeTables.InRecord
  val inRecord : InRecord = edgeTables.inRecord 
  
  type EdgeId = edgeTables.EdgeId
  val edgeId : EdgeId = edgeTables.edgeId

 
  implicit val containEdgeId : EdgeId ∈ EdgeRecord#Properties = edgeType.containsId.asInstanceOf[EdgeId ∈ EdgeRecord#Properties]
  implicit val edgeIdLookup : Lookup[EdgeRecord#Raw, EdgeId#Rep] = edgeType.idLookup.asInstanceOf[Lookup[EdgeRecord#Raw, EdgeId#Rep]]
  
  implicit val containTargetId : TargetId ∈ EdgeRecord#Properties = edgeType.containsTargetId.asInstanceOf[TargetId ∈ EdgeRecord#Properties]
  implicit val targetIdLookup : Lookup[EdgeRecord#Raw, TargetId#Rep] = edgeType.targetLookup.asInstanceOf[Lookup[EdgeRecord#Raw, TargetId#Rep]]
  
  implicit val containSourceId : SourceId ∈ EdgeRecord#Properties  = edgeType.containsSourceId.asInstanceOf[SourceId ∈ EdgeRecord#Properties]
  implicit val sourceIdLookup : Lookup[EdgeRecord#Raw, SourceId#Rep] = edgeType.sourceLookup.asInstanceOf[Lookup[EdgeRecord#Raw, SourceId#Rep]]
  
  
  
  def write(edgeItemValue: TaggedWith[EdgeRecord])(implicit transf: From.Item[EdgeItem, SDKRep]): List[AnyPutItemAction] =  ???/*{

    val inRep = edgeTables.inItem fields ( 
      edgeTables.getInRecordRep(edgeItemValue.get((targetId : edgeTables.TargetId)), edgeItemValue.get(edgeId))
    )

    val outRep = edgeTables.outItem  fields (

        edgeTables.outRecord ->> (
        (sourceId is edgeItemValue.get(sourceId)) :~:
        (edgeId is edgeItemValue.get(edgeId)) :~:
        ∅)
    )

    val inTableRequest  = InCompositeKeyTable(edgeTables.inTable,   Active(edgeTables.inTable,    ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeTables.inItem  withValue inRep
    val outTableRequest = InCompositeKeyTable(edgeTables.outTable,  Active(edgeTables.outTable,   ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeTables.outItem withValue outRep
    val tableRequest    = InHashKeyTable(edgeTable, Active(edgeTable,  ServiceProvider.service.account,
      ThroughputStatus(1,1))) putItem edgeItem    withValue (edgeItem ->> edgeItemValue)

    List(inTableRequest, outTableRequest, tableRequest)
  }*/
}

class EdgeWriter[ET <: Singleton with AnyEdgeTables](val edgeTables: ET) extends AnyEdgeWriter {

  type EdgeTables = ET

}

