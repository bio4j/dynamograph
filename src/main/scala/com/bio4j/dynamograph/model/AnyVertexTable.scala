package com.bio4j.dynamograph.model

import ohnosequences.tabula.AnyRegion
import ohnosequences.scarph.AnyVertexType
import ohnosequences.tabula.AnyHashKeyTable
import ohnosequences.tabula.AnyTable
import com.bio4j.dynamograph.model.Properties._
import ohnosequences.tabula.HashKeyTable

trait AnyVertexTable {
  type VertexType <: Singleton with AnyVertexType
  val vertexType : VertexType
  
  type Region <: AnyRegion
  val region : Region
  
  type Table <: Singleton with AnyTable.inRegion[Region] with
                    AnyHashKeyTable.withKey[id.type]
  val table : Table   
}

 abstract class VertexTable[
   VT <: Singleton with AnyVertexType,
   R <: AnyRegion
 ](
   val vertexType : VT,
   val tableName : String,
   val region: R
) extends AnyVertexTable{
    type VertexType = VT
    type Region = R
    
    type Table = Table.type; val table : Table = Table  
    case object Table extends HashKeyTable(tableName, id, region)
  }