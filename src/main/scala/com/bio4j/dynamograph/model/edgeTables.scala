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

  type EdgeType <: Singleton with AnyEdgeTypeWithId //{
  //     // shouldn't be needed
  //     type SourceType <: Singleton with AnyVertexTypeWithId
  //     type TargetType <: Singleton with AnyVertexTypeWithId
  //   

  val edgeType: EdgeType

  // all these types are accessible through EdgeType; they are not actually needed (unless a type inference bug forces us to do so)
  type EdgeId = edgeType.Id
  val edgeId: EdgeId = edgeType.id

  type TargetId = edgeType.TargetId 
  val targetId : TargetId = edgeType.targetId

  type SourceId = edgeType.SourceId
  val sourceId: SourceId = edgeType.sourceId
  
  type Region <: AnyRegion
  val region: Region 

  // tables
  type OutTable <:  Singleton with AnyTable.inRegion[Region] with
                    AnyCompositeKeyTable.withHashKey[SourceId] with
                    AnyCompositeKeyTable.withRangeKey[EdgeId]

  val outTable: OutTable
  
  type OutRecord = OutRecord.type; val outRecord: OutRecord = OutRecord
  case object OutRecord extends Record(sourceId :~: edgeId :~: ∅)

  type OutItem <: Singleton with AnyItem 
                  with AnyItem.ofTable[OutTable]
                  with AnyItem.withRecord[OutRecord]

  val outItem : OutItem

  type EdgeTable <: Singleton with AnyHashKeyTable 
                    with AnyTable.inRegion[Region] 
                    with AnyHashKeyTable.withKey[EdgeId]

  val edgeTable: EdgeTable
  
  type EdgeRecord = edgeType.Record;
  val edgeRecord : EdgeRecord = edgeType.record

  type EdgeItem <:  Singleton with AnyItem 
                    with AnyItem.ofTable[EdgeTable]
                    with AnyItem.withRecord[EdgeRecord]

  val edgeItem : EdgeItem  

  type InTable <: Singleton with AnyTable.inRegion[Region]
                  with AnyCompositeKeyTable.withHashKey[TargetId]
                  with AnyCompositeKeyTable.withRangeKey[EdgeId]

  val inTable: InTable

  type InRecord = InRecord.type; val inRecord: InRecord = InRecord
  case object InRecord extends Record(targetId :~: edgeId :~: ∅)

  type InItem <:  Singleton with AnyItem 
                  with AnyItem.ofTable[edgeTables.InTable]
                  with AnyItem.withRecord[edgeTables.InRecord]

  val inItem : InItem

  implicit val recordValuesAreOK: everyElementOf[EdgeRecord#Raw]#isOneOf[ValidValues]

  val tables = inTable :: outTable :: edgeTable :: HNil
  
}

object AnyEdgeTables{
  
  type withEdgeType[ET <: Singleton with AnyEdgeTypeWithId] = AnyEdgeTables {type EdgeType = ET}
}

class EdgeTables[
  ET <: Singleton with AnyEdgeTypeWithId,
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
  
  type OutTable = OutTable.type; val outTable = OutTable
  case object OutTable  extends CompositeKeyTable(s"${tableName}_OUT", sourceId, edgeId, region)
  type OutItem = OutItem.type; val outItem = OutItem
  case object OutItem   extends Item(outTable, outRecord)

  type EdgeTable = EdgeTable.type; val edgeTable = EdgeTable
  case object EdgeTable extends HashKeyTable(tableName, relationId, region)
  type EdgeRecord = EdgeType#Record;
  val edgeRecord : EdgeRecord = edgeType.record
  type EdgeItem = EdgeItem.type; val edgeItem = EdgeItem
  case object EdgeItem  extends Item(edgeTable, edgeRecord)

  type InTable = InTable.type; val inTable = InTable
  case object InTable   extends CompositeKeyTable(s"{tableName}_IN", targetId, edgeId, region)
  type InItem = InItem.type; val inItem = InItem
  case object InItem    extends Item(inTable, inRecord)
}