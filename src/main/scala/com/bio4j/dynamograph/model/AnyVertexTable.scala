package com.bio4j.dynamograph.model

import ohnosequences.tabula.AnyRegion
import ohnosequences.scarph.AnyVertexType
import ohnosequences.tabula.AnyHashKeyTable
import ohnosequences.tabula.AnyTable
import com.bio4j.dynamograph.model.Properties._
import ohnosequences.tabula.HashKeyTable
import com.bio4j.dynamograph.AnyVertexTypeWithId

trait AnyVertexTable {
  type VertexType <: Singleton with AnyVertexTypeWithId
  val vertexType : VertexType
  
  type Region <: AnyRegion
  val region : Region
  
  type Table <: Singleton with AnyTable.inRegion[Region] with
                    AnyHashKeyTable.withKey[vertexType.Id]
  val table : Table   
}

object AnyVertexTable{
  type withVertexType[VT <: Singleton with AnyVertexTypeWithId] = AnyVertexTable{type VertexType = VT}
}

 abstract class VertexTable[
   VT <: Singleton with AnyVertexTypeWithId,
   R <: AnyRegion
 ](
   val vertexType : VT,
   val tableName : String,
   val region: R
) extends AnyVertexTable{
    type VertexType = VT
    type Region = R
    
    type Table = Table.type; val table : Table = Table  
    case object Table extends HashKeyTable(tableName, vertexType.id, region)
  }
