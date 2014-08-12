package com.bio4j.dynamograph.model

import ohnosequences.scarph.AnyEdgeType
import ohnosequences.tabula.AnyRegion
import ohnosequences.tabula.AnyTable
import ohnosequences.tabula.AnyCompositeKeyTable
import com.bio4j.dynamograph.model.Properties._
import ohnosequences.tabula.AnyHashKeyTable
import ohnosequences.tabula.CompositeKeyTable
import ohnosequences.tabula.HashKeyTable

trait AnyEdgeTables {
  type EdgeType <: Singleton with AnyEdgeType
  val edgeType : EdgeType
  
  type Region <: AnyRegion
  val region : Region
  
   type OutTable <:  Singleton with AnyTable.inRegion[Region] with
                    AnyCompositeKeyTable.withHashKey[sourceId.type] with
                    AnyCompositeKeyTable.withRangeKey[relationId.type]
  val outTable : OutTable
  
   type InTable <:  Singleton with AnyTable.inRegion[Region] with
                    AnyCompositeKeyTable.withHashKey[targetId.type] with
                    AnyCompositeKeyTable.withRangeKey[relationId.type]
  val inTable : InTable
  
  type EdgeTable <: Singleton with AnyTable.inRegion[Region] with
                    AnyHashKeyTable.withKey[relationId.type]
  val edgeTable : EdgeTable                   
                   
}

abstract class EdgeTables[
  ET <: AnyEdgeType,
  R <: AnyRegion
](
  val edgeType : ET,
  val tableName: String, 
  val region: R
) {
    type Region = R
    type EdgeType = ET
    
    type InTable = InTable.type; val inTable : InTable = InTable 
    case object InTable extends CompositeKeyTable(s"${tableName}_IN", id, relationId, region)
    
    type OutTable = OutTable.type; val outTable : OutTable = OutTable
    case object OutTable extends CompositeKeyTable(s"${tableName}_OUT", id, relationId, region)
    
    type EdgeTable = EdgeTable.type; val edgeTable : EdgeTable = EdgeTable  
    case object EdgeTable extends HashKeyTable(tableName, relationId, region)
  }