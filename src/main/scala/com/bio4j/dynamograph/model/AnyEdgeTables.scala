package com.bio4j.dynamograph.model

import ohnosequences.scarph.AnyEdgeType
import ohnosequences.tabula.AnyRegion
import ohnosequences.tabula.AnyTable
import ohnosequences.tabula.AnyCompositeKeyTable
import com.bio4j.dynamograph.model.Properties._
import ohnosequences.tabula.AnyHashKeyTable
import ohnosequences.tabula.CompositeKeyTable
import ohnosequences.tabula.HashKeyTable
import shapeless._
import com.bio4j.dynamograph.AnyEdgeTypeWithId

trait AnyEdgeTables {
  type EdgeType <: Singleton with AnyEdgeTypeWithId
  val edgeType : EdgeType
  
  type Region <: AnyRegion
  val region : Region
  
   type OutTable <:  Singleton with AnyTable.inRegion[Region] with
                    AnyCompositeKeyTable.withHashKey[edgeType.SourceId] with
                    AnyCompositeKeyTable.withRangeKey[edgeType.Id]
  val outTable : OutTable
  
   type InTable <:  Singleton with AnyTable.inRegion[Region] with
                    AnyCompositeKeyTable.withHashKey[edgeType.TargetId] with
                    AnyCompositeKeyTable.withRangeKey[edgeType.Id]
  val inTable : InTable
  
  type EdgeTable <: Singleton with AnyTable.inRegion[Region] with
                    AnyHashKeyTable.withKey[edgeType.Id]
  val edgeTable : EdgeTable                   

}

abstract class EdgeTables[
  ET <: Singleton with AnyEdgeTypeWithId,
  R <: AnyRegion
](
  val edgeType : ET,
  val tableName: String, 
  val region: R
) extends AnyEdgeTables{
    type Region = R
    type EdgeType = ET
    
    type InTable = InTable.type; val inTable : InTable = InTable 
    case object InTable extends CompositeKeyTable(s"${tableName}_IN", edgeType.targetId, edgeType.id, region)
    
    type OutTable = OutTable.type; val outTable : OutTable = OutTable
    case object OutTable extends CompositeKeyTable(s"${tableName}_OUT", edgeType.sourceId, edgeType.id, region)
    
    type EdgeTable = EdgeTable.type; val edgeTable : EdgeTable = EdgeTable  
    case object EdgeTable extends HashKeyTable(tableName, edgeType.id, region)
    
    val tables = edgeTable :: inTable :: outTable :: HNil
  }