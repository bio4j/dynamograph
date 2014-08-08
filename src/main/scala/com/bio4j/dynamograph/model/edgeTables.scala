package com.bio4j.dynamograph.model

import ohnosequences.scarph._
import ohnosequences.typesets._
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._
import shapeless._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._

trait AnyEdgeTables { edgeTables =>

  // TODO missing sealed edge type in scarph
  type EdgeType <: Singleton with AnySealedEdgeType {
      type SourceType <: Singleton with AnySealedVertexType
      type TargetType <: Singleton with AnySealedVertexType
    }
  val edgeType: EdgeType

  type EdgeId <: Singleton with AnyProperty.ofValue[String]
  val edgeId: EdgeId

  type Region <: AnyRegion
  val region: Region

  type SourceVertexTable <: AnyVertexTable with AnyVertexTable.withVertexType[edgeTables.EdgeType#SourceType]
  val sourceVertexTable: SourceVertexTable

  

  type OutTable <:  Singleton with AnyTable.inRegion[edgeTables.Region] with
                    AnyCompositeKeyTable.withHashKey[edgeTables.SourceVertexTable#VertexId] with
                    AnyCompositeKeyTable.withRangeKey[edgeTables.EdgeId]

  val outTable: OutTable

  type EdgeTable <: Singleton with AnyHashKeyTable with AnyTable.inRegion[edgeTables.Region] with
                    AnyHashKeyTable.withKey[edgeTables.EdgeId]

  val edgeTable: EdgeTable


  type InTable <: Singleton with AnyTable.inRegion[edgeTables.Region] with
                  AnyCompositeKeyTable.withHashKey[edgeTables.TargetVertexTable#VertexId] with
                  AnyCompositeKeyTable.withRangeKey[edgeTables.EdgeId]

  val inTable: InTable

  type InRecord = InRecord.type; val inRecord = InRecord
  case object InRecord    extends Record(targetVertexTable.vertexId :~: edgeId :~: ∅)

  type InItem <:  Singleton with AnyItem with
                  AnyItem.ofTable[edgeTables.InTable] with
                  AnyItem.withRecord[edgeTables.InRecord]
  val inItem : InItem

  type OutRecord = OutRecord.type; val outRecord = OutRecord
  case object OutRecord   extends Record(sourceVertexTable.vertexId :~: edgeId :~: ∅)
  type OutItem <: Singleton with AnyItem with
                  AnyItem.ofTable[edgeTables.OutTable] with
                  AnyItem.withRecord[edgeTables.OutRecord]
  val outItem : OutItem


  type EdgeRecord = edgeType.Record;
  val edgeRecord : EdgeRecord = edgeType.record
  type EdgeItem <:Singleton with AnyItem with
                  AnyItem.ofTable[edgeTables.EdgeTable] with
                  AnyItem.withRecord[edgeTables.EdgeRecord]
  val edgeItem : EdgeItem

  val recordValuesAreOK: everyElementOf[EdgeType#Record#Values]#isOneOf[ValidValues]

  type TargetVertexTable <: AnyVertexTable with AnyVertexTable.withVertexType[edgeTables.EdgeType#TargetType]
  val targetVertexTable: TargetVertexTable

  val tables = inTable :: outTable :: edgeTable :: HNil
}

class EdgeTables[
  ST <: AnyVertexTable with AnyVertexTable.withVertexType[ET#SourceType],
  ET <: Singleton with AnySealedEdgeType {
    type SourceType <: Singleton with AnySealedVertexType
    type TargetType <: Singleton with AnySealedVertexType
  },
  TT <: AnyVertexTable with AnyVertexTable.withVertexType[ET#TargetType],
  R <: AnyRegion
](
  val sourceVertexTable: ST,
  val edgeType: ET,
  val targetVertexTable: TT,
  val tableName: String,
  val region: R
)
 (implicit
  val recordValuesAreOK: everyElementOf[ET#Record#Values]#isOneOf[ValidValues]
   )
extends AnyEdgeTables {
  
  type EdgeType = ET
  type Region = R
  type SourceVertexTable = ST
  type TargetVertexTable = TT

  // TODO constructor params
  type EdgeId = relationId.type
  val edgeId = relationId

  type OutTable = OutTable.type; val outTable = OutTable
  case object OutTable  extends CompositeKeyTable(s"${tableName}_OUT", sourceVertexTable.vertexId, edgeId, region)

  type OutItem = OutItem.type; val outItem = OutItem
  case object OutItem   extends Item(outTable, outRecord)

  type EdgeTable = EdgeTable.type; val edgeTable = EdgeTable
  case object EdgeTable extends HashKeyTable(tableName, relationId, region)

  type EdgeRecord = EdgeType#Record;
  val edgeRecord : EdgeRecord = edgeType.record

  type EdgeItem = EdgeItem.type; val edgeItem = EdgeItem
  case object EdgeItem  extends Item(edgeTable, edgeRecord)

  type InTable = InTable.type; val inTable = InTable
  case object InTable   extends CompositeKeyTable(s"{tableName}_IN", targetVertexTable.vertexId, edgeId, region)

  type InItem = InItem.type; val inItem = InItem
  case object InItem    extends Item(inTable, inRecord)


}