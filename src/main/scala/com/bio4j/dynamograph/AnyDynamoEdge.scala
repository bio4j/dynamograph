package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._


trait AnyDynamoEdge extends AnySealedEdge { dynamoEdge =>

  type Tpe <: Singleton with AnyEdgeTypeWithId
  
  val dao: AnyDynamoDbDao = ServiceProvider.dao
  
  type Other = String

  type Source <:  AnyDynamoVertex
                  with AnyVertex { type Tpe <: Singleton with AnyVertexTypeWithId with dynamoEdge.Tpe#SourceType }
  val source: Source

  type Target <:  AnyDynamoVertex 
                  with AnyVertex { type Tpe <: Singleton with AnyVertexTypeWithId with dynamoEdge.Tpe#TargetType }
  val target: Target
  
  implicit val containSourceId : sourceId.type ∈ tpe.record.Properties = tpe.containSourceId
  implicit val containTargetId : targetId.type ∈ tpe.record.Properties = tpe.containTargetId
  implicit val sourceLookup : Lookup[tpe.record.Raw,sourceId.Rep]
  implicit val targetLookup : Lookup[tpe.record.Raw,targetId.Rep]


  object sourceGetter extends GetSource {

    def apply(rep: dynamoEdge.Rep): Out = source ->> {

      val srcId = rep.get(sourceId)
      val couldBeRecordEntry = dao.get[source.tpe.type]( srcId, source.tpe )
      val recordEntry = couldBeRecordEntry.right.get
      source.raw ( recordEntry, "" )
    }
  }

  implicit object targetGetter extends GetTarget {

    def apply(rep: dynamoEdge.Rep): target.Rep = target ->> {

      val tgtId = rep.get(targetId)
      val couldBeRecordEntry = dao.get[target.tpe.type]( tgtId, target.tpe )
      val recordEntry = couldBeRecordEntry.right.get
      target.raw ( recordEntry, "" )
    }
  }
}

class DynamoEdge[
ET <: Singleton with AnyEdgeTypeWithId,
S <: Singleton with AnyDynamoVertex.ofType[ET#SourceType] with AnyDynamoVertex,
T <: Singleton with AnyDynamoVertex.ofType[ET#TargetType] with AnyDynamoVertex
](val source: S, val tpe: ET, val target: T) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
}

object AnyDynamoEdge{
  type ofType[ET <: Singleton with AnyEdgeTypeWithId] = AnyDynamoEdge { type Tpe = ET }
}
