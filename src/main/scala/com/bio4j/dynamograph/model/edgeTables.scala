package com.bio4j.dynamograph.model

import ohnosequences.scarph._
import ohnosequences.typesets._
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._
import shapeless._
import com.bio4j.dynamograph.{AnyDynamoVertex, AnyDynamoEdge}
import com.bio4j.dynamograph.model.GeneralSchema._

trait AnyEdgeTables { edgeTables =>

  // TODO missing sealed edge type in scarph
  type EdgeType <: Singleton with AnyEdgeType {
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

  

  type OutTable <:  AnyTable.inRegion[edgeTables.Region] with 
                    AnyCompositeKeyTable.withHashKey[edgeTables.SourceVertexTable#VertexId] with
                    AnyCompositeKeyTable.withRangeKey[edgeTables.EdgeId]

  val outTable: OutTable

  type EdgeTable <: AnyHashKeyTable with AnyTable.inRegion[edgeTables.Region] with
                    AnyHashKeyTable.withKey[edgeTables.EdgeId]

  val edgeTable: EdgeTable
  
  type InTable <: AnyTable.inRegion[edgeTables.Region] with 
                  AnyCompositeKeyTable.withHashKey[edgeTables.TargetVertexTable#VertexId] with
                  AnyCompositeKeyTable.withRangeKey[edgeTables.EdgeId]

  val inTable: InTable

  type TargetVertexTable <: AnyVertexTable with AnyVertexTable.withVertexType[edgeTables.EdgeType#TargetType]
  val targetVertexTable: TargetVertexTable

  val tables = inTable :: outTable :: edgeTable :: HNil
}

class EdgeTables[
  ST <: AnyVertexTable with AnyVertexTable.withVertexType[ET#SourceType],
  ET <: Singleton with AnyEdgeType {
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
extends AnyEdgeTables {
  
  type EdgeType = ET
  type Region = R
  type SourceVertexTable = ST
  type TargetVertexTable = TT

  // TODO constructor params
  type EdgeId = id.type
  val edgeId = id

  type OutTable = OutTable.type; val outTable = OutTable
  case object OutTable  extends CompositeKeyTable(s"${tableName}_OUT", sourceVertexTable.vertexId, edgeId, region)

  type EdgeTable = EdgeTable.type; val edgeTable = EdgeTable
  case object EdgeTable extends HashKeyTable(tableName, relationId, region)

  type InTable = InTable.type; val inTable = InTable
  case object InTable   extends CompositeKeyTable(s"{tableName}_IN", targetVertexTable.vertexId, edgeId, region)
    
  case object outRecord   extends Record(sourceVertexTable.vertexId :~: edgeId :~: ∅)
  case object edgeRecord  extends Record(edgeId :~: sourceVertexTable.vertexId :~: targetVertexTable.vertexId :~: ∅)
  case object inRecord    extends Record(targetVertexTable.vertexId :~: edgeId :~: ∅)

  case object outItem   extends Item(outTable, outRecord)
  case object edgeItem  extends Item(edgeTable, edgeRecord)
  case object inItem    extends Item(inTable, inRecord)
}