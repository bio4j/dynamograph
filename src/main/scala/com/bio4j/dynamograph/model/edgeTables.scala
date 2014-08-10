package com.bio4j.dynamograph.model

import ohnosequences.scarph._
import ohnosequences.typesets._
import ohnosequences.tabula._
import shapeless._
import ohnosequences.tabula.AnyItem._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.AnyEdgeTypeWithId
import com.bio4j.dynamograph.AnyVertexTypeWithId

trait AnyEdgeTables { edgeTables =>

  type EdgeType <: Singleton with AnyEdgeTypeWithId {
      type SourceType <: Singleton with AnyVertexTypeWithId
      type TargetType <: Singleton with AnyVertexTypeWithId
    }
  val edgeType: EdgeType

  type InVertexId = edgeTables.edgeType.targetType.Id
  val inVertexId: InVertexId = edgeTables.edgeType.targetType.id
  type OutVertexId = edgeTables.edgeType.sourceType.Id
  val outVertexId: OutVertexId = edgeTables.edgeType.sourceType.id
  
    
  type EdgeId = edgeType.Id
  val edgeId: EdgeId = edgeType.id
  type ContaintEdgeId = EdgeId ∈ EdgeRecord#Properties 
  val containEdgeId : ContaintEdgeId
  type EdgeIdLookup = Lookup[EdgeRecord#Raw, edgeId.Rep]
  val edgeIdLookup : EdgeIdLookup
  
  type SourceId = edgeType.SourceId
  val srcId : SourceId = edgeType.srcId 
  type ContaintSourceId = SourceId ∈ EdgeRecord#Properties 
  val containSourceId : ContaintSourceId
  type SourceIdLookup = Lookup[EdgeRecord#Raw, srcId.Rep]
  val sourceIdLookup : SourceIdLookup
  
  type TargetId = edgeType.TargetId
  val tgtId : TargetId = edgeType.tgtId 
  type ContaintTargetId = TargetId ∈ EdgeRecord#Properties 
  val containTargetId : ContaintTargetId
  type TargetIdLookup = Lookup[EdgeRecord#Raw, tgtId.Rep]
  val targetIdLookup : TargetIdLookup

  type Region <: AnyRegion
  val region: Region 

  type OutTable <:  Singleton with AnyTable.inRegion[edgeTables.Region] with
                    AnyCompositeKeyTable.withHashKey[OutVertexId] with
                    AnyCompositeKeyTable.withRangeKey[edgeTables.EdgeId]
  val outTable: OutTable
  
  type OutRecord = OutRecord.type; val outRecord = OutRecord
  case object OutRecord   extends Record(outVertexId :~: edgeId :~: ∅)
  type OutItem <: Singleton with AnyItem with
                  AnyItem.ofTable[edgeTables.OutTable] with
                  AnyItem.withRecord[edgeTables.OutRecord]
  val outItem : OutItem

  type EdgeTable <: Singleton with AnyHashKeyTable with AnyTable.inRegion[edgeTables.Region] with
                    AnyHashKeyTable.withKey[edgeTables.EdgeId]
  val edgeTable: EdgeTable
  
  type EdgeRecord = edgeType.Record;
  val edgeRecord : EdgeRecord = edgeType.record
  type EdgeItem <:Singleton with AnyItem with
                  AnyItem.ofTable[edgeTables.EdgeTable] with
                  AnyItem.withRecord[edgeTables.EdgeRecord]
  val edgeItem : EdgeItem  

  type InTable <: Singleton with AnyTable.inRegion[edgeTables.Region] with
                  AnyCompositeKeyTable.withHashKey[edgeTables.InVertexId] with
                  AnyCompositeKeyTable.withRangeKey[edgeTables.EdgeId]
  val inTable: InTable

  type InRecord = InRecord.type; val inRecord = InRecord
  case object InRecord    extends Record(inVertexId :~: edgeId :~: ∅)
  type InItem <:  Singleton with AnyItem with
                  AnyItem.ofTable[edgeTables.InTable] with
                  AnyItem.withRecord[edgeTables.InRecord]
  val inItem : InItem

  val recordValuesAreOK: everyElementOf[EdgeType#Record#Values]#isOneOf[ValidValues]

  val tables = inTable :: outTable :: edgeTable :: HNil
}

class EdgeTables[
  ET <: Singleton with AnyEdgeTypeWithId {
    type SourceType <: Singleton with AnyVertexTypeWithId
    type TargetType <: Singleton with AnyVertexTypeWithId
  },
  R <: AnyRegion
](
  val edgeType: ET,
  val tableName: String,
  val region: R
)
 (implicit
  val recordValuesAreOK: everyElementOf[ET#Record#Values]#isOneOf[ValidValues]
   )
extends AnyEdgeTables {
  
  type EdgeType = ET
  type Region = R
  
  val containEdgeId = edgeType.containId
  val edgeIdLookup = edgeType.idLookup
  
  val containSourceId = edgeType.containSourceId
  val sourceIdLookup = edgeType.sourceLookup
  
  val containTargetId = edgeType.containTargetId
  val targetIdLookup = edgeType.targetLookup

  type OutTable = OutTable.type; val outTable = OutTable
  case object OutTable  extends CompositeKeyTable(s"${tableName}_OUT", outVertexId, edgeId, region)
  type OutItem = OutItem.type; val outItem = OutItem
  case object OutItem   extends Item(outTable, outRecord)

  type EdgeTable = EdgeTable.type; val edgeTable = EdgeTable
  case object EdgeTable extends HashKeyTable(tableName, relationId, region)
  type EdgeRecord = EdgeType#Record;
  val edgeRecord : EdgeRecord = edgeType.record
  type EdgeItem = EdgeItem.type; val edgeItem = EdgeItem
  case object EdgeItem  extends Item(edgeTable, edgeRecord)

  type InTable = InTable.type; val inTable = InTable
  case object InTable   extends CompositeKeyTable(s"{tableName}_IN", inVertexId, edgeId, region)
  type InItem = InItem.type; val inItem = InItem
  case object InItem    extends Item(inTable, inRecord)


}