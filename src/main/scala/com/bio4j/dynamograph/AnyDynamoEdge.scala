package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao}
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.model._
import ohnosequences.typesets._
import com.bio4j.dynamograph.writer.AnyVertexWriter
import com.bio4j.dynamograph.reader.AnyVertexReader
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._, impl._, impl.actions._


trait AnyDynamoEdge extends AnySealedEdge { dynamoEdge =>

  type Tpe <: Singleton with AnyEdgeTypeWithId
  
  val dao: AnyDynamoDbDao = ServiceProvider.dao

  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables : EdgeTables
  
  type SourceTable <: Singleton with AnyVertexTable.withVertexType[Tpe#SourceType]
  val sourceTable : SourceTable
  type SourceReader <: Singleton with AnyVertexReader.withVertexTableType[SourceTable]
  val sourceReader : SourceReader
  
  type TargetTable <: Singleton with AnyVertexTable.withVertexType[Tpe#TargetType]
  val targetTable : TargetTable
  type TargetReader <: Singleton with AnyVertexReader.withVertexTableType[TargetTable]
  val targetReader : TargetReader
  
  /*
    you need the tables here!! same for DynamoVertex, but there is less obvious. If you would have them, what you need to do is:

    1. use sourceId, targetId from there to get the id from the in/out table
    2. get the vertex from the corresponding vertex table

  */
   
  type Other = String

  type Source <:  AnyDynamoVertex { type Tpe <: Singleton with AnyVertexTypeWithId with dynamoEdge.tpe.SourceType }

  val source: Source

  type Target <:  AnyDynamoVertex { type Tpe <: Singleton with AnyVertexTypeWithId with dynamoEdge.tpe.TargetType }

  val target: Target

  implicit object sourceGetter extends GetSource {

    import tpe._

    def apply(rep: dynamoEdge.Rep): Out = source ->> {
                	  
      val srcId: sourceId.Rep = rep get sourceId

      // try to get it
      import ServiceProvider.executors._
      
      val getResult = ServiceProvider.service please (

        FromHashKeyTable (

          source.vertexTable.table, 
          Active (
            source.vertexTable.table,
            ServiceProvider.service.account,
            ThroughputStatus(1, 1)
          )
        ) getItem source.vertexTable.vertexItem withKey (srcId)
      )

      val recordEntry = getResult.right.get

      source.raw ( recordEntry, "" )
    }
  }

  implicit object targetGetter extends GetTarget {

    import tpe._

    def apply(rep: dynamoEdge.Rep): target.Rep = target ->> {

      val tgtId = rep get targetId
      val couldBeRecordEntry = targetReader.read(tgtId)
      val recordEntry = couldBeRecordEntry.right.get
      target.raw ( recordEntry, "" )
    }
  }
}

class DynamoEdge[
  ET <: Singleton with AnyEdgeTypeWithId,
  ETab <: Singleton with AnyEdgeTables.withEdgeType[ET],
  S <: Singleton with AnyDynamoVertex.ofType[ET#SourceType] with AnyDynamoVertex,
  STab <: Singleton with AnyVertexTable.withVertexType[ET#SourceType],
  SR <: Singleton with AnyVertexReader.withVertexTableType[STab],
  T <: Singleton with AnyDynamoVertex.ofType[ET#TargetType] with AnyDynamoVertex,
  TTab <: Singleton with AnyVertexTable.withVertexType[ET#TargetType],
  TR <: Singleton with AnyVertexReader.withVertexTableType[TTab]
](
  val source: S,
  val sourceTable: STab,
  val sourceReader: SR,
  val tpe: ET, 
  val edgeTables: ETab, 
  val target: T, 
  val targetTable : TTab,
  val targetReader : TR
) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
  type EdgeTables = ETab
  type SourceTable = STab
  type TargetTable = TTab
  type SourceReader = SR
  type TargetReader = TR
}

object AnyDynamoEdge{
  type ofType[ET <: Singleton with AnyEdgeTypeWithId] = AnyDynamoEdge { type Tpe = ET }
}
