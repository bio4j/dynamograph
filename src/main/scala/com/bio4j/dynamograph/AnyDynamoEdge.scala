package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets.{Property, AnyProperty}


trait AnyDynamoEdge extends AnySealedEdge { dynamoEdge =>


  val dao: AnyDynamoDbDao = ServiceProvider.dao

  type Source <:  AnyDynamoVertex
                  with AnyVertex { type Tpe <: Singleton with AnySealedVertexType with dynamoEdge.Tpe#SourceType }
  val source: Source

  type Target <:  AnyDynamoVertex 
                  with AnyVertex { type Tpe <: Singleton with AnySealedVertexType with dynamoEdge.Tpe#TargetType }
  val target: Target


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

  // NOTE: why was it private?
  def getValue[P <: AnyProperty](rep: Rep, p : P) : String = rep.get(p)

}

class DynamoEdge[
ET <: Singleton with AnySealedEdgeType,
S <: Singleton with AnyDynamoVertex.ofType[ET#SourceType] with AnyDynamoVertex,
T <: Singleton with AnyDynamoVertex.ofType[ET#TargetType] with AnyDynamoVertex
](val source: S, val tpe: ET, val target: T) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
}

object AnyDynamoEdge{
  type ofType[ET <: Singleton with AnySealedEdgeType] = AnyDynamoEdge { type Tpe = ET }
}
