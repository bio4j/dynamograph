package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets._


trait AnyDynamoEdge extends AnySealedEdge { dynamoEdge =>

  type Tpe <: Singleton with AnyEdgeTypeWithId
  
  val dao: AnyDynamoDbDao = ServiceProvider.dao

  /*
    you need the tables here!! same for DynamoVertex, but there is less obvious. If you would have them, what you need to do is:

    1. use sourceId, targetId from there to get the id from the in/out table
    2. get the vertex from the corresponding vertex table

  */
  
  type Other = String

  type Source <:  AnyDynamoVertex
                  with AnyVertex { type Tpe <: Singleton with AnyVertexTypeWithId with dynamoEdge.Tpe#SourceType }
  val source: Source

  type Target <:  AnyDynamoVertex 
                  with AnyVertex { type Tpe <: Singleton with AnyVertexTypeWithId with dynamoEdge.Tpe#TargetType }
  val target: Target

  object sourceGetter extends GetSource {

    def apply(rep: dynamoEdge.Rep): Out = source ->> {

      val srcId = rep.get(source.tpe.id: Tpe#SourceType#Id)
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
