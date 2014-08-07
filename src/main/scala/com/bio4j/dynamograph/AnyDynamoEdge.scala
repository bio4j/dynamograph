package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao}
import com.bio4j.dynamograph.model.GeneralSchema._
import ohnosequences.typesets.{Property, AnyProperty}


trait AnyDynamoEdge extends AnyEdge { dynamoEdge =>


  final type Raw = Map[String, AttributeValue]

  val dao: AnyDynamoDbDao = ServiceProvider.dao

  type Source <:  AnyDynamoVertex
                  with AnyVertex { type Tpe <: Singleton with AnySealedVertexType with dynamoEdge.Tpe#SourceType }
  
  val source: Source

  type Target <:  AnyDynamoVertex 
                  with AnyVertex { type Tpe <: Singleton with AnySealedVertexType with dynamoEdge.Tpe#TargetType }

  val target: Target


  implicit def unsafeGetProperty[P <: AnyProperty: Property.Of[this.Tpe]#is](p: P) =
    new PropertyGetter[P](p) {
      def apply(rep: Rep) : p.Raw = getValue(rep, p).asInstanceOf[p.Raw]
    }

  object sourceGetter extends GetSource {

    def apply(rep: dynamoEdge.Rep): Out = source ->> {

      val srcId = getValue(rep, sourceId)

      val couldBeRecordEntry = dao.get[source.tpe.type]( srcId, source.tpe )

      val recordEntry = couldBeRecordEntry.right.get

      source.raw ( recordEntry, "" )
    }
  }

  implicit object targetGetter extends GetTarget {

    def apply(rep: dynamoEdge.Rep): target.Rep = target ->> {

      val tgtId = getValue(rep, targetId)

      val couldBeRecordEntry = dao.get[target.tpe.type]( tgtId, target.tpe )
      
      val recordEntry = couldBeRecordEntry.right.get

      target.raw ( recordEntry, "" )
    }
  }

  // NOTE: why was it private?
  def getValue[P <: AnyProperty](rep: Rep, p : P) : String = rep.get(p.label).getOrElse(new AttributeValue().withS("")).getS

}

class DynamoEdge[
ET <: AnyEdgeType,
S <: Singleton with AnyDynamoVertex.ofType[ET#SourceType] with AnyDynamoVertex,
T <: Singleton with AnyDynamoVertex.ofType[ET#TargetType] with AnyDynamoVertex
](val source: S, val tpe: ET, val target: T) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
}

object AnyDynamoEdge{
  type ofType[ET <: AnyEdgeType] = AnyDynamoEdge { type Tpe = ET }
}
