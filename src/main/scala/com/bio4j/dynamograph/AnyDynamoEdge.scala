package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import ohnosequences.typesets._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao}
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.AnyVertexTable


trait AnyDynamoEdge extends AnyEdge { dynamoEdge =>


  final type Raw = Map[String, AttributeValue]

  val dao: AnyDynamoDbDao = ServiceProvider.dao

  type Source <: AnyDynamoVertex.ofType[Tpe#SourceType] with AnyDynamoVertex
  val source: Source

  type Target <: AnyDynamoVertex.ofType[Tpe#TargetType] with AnyDynamoVertex
  val target: Target
  
  type SourceTable <: Singleton with AnyVertexTable.ofType[Tpe#SourceType]
  val sourceTable : SourceTable
  
  type TargetTable <: Singleton with AnyVertexTable.ofType[Tpe#TargetType]
  val targetTable : TargetTable


  implicit def unsafeGetProperty[P <: AnyProperty: Property.Of[this.Tpe]#is](p: P) =
    new PropertyGetter[P](p) {
      def apply(rep: Rep) : p.Raw = getValue(rep, p).asInstanceOf[p.Raw]
    }

  implicit object sourceGetter extends GetSource {
    def apply(rep: dynamoEdge.Rep): Out =
      source ->> dao.get(getValue(rep,sourceId), tpe.et.sourceType)
  }

  implicit object targetGetter extends GetTarget {
    def apply(rep: dynamoEdge.Rep): Out =
      target ->> dao.get(getValue(rep,targetId), tpe.et.targetType)
  }

  // NOTE: why was it private?
  def getValue[P <: AnyProperty](rep: Rep, p : P) : String = rep.get(p.label).getOrElse(new AttributeValue().withS("")).getS

}

class DynamoEdge[
  ET <: AnyEdgeType,
  S <: Singleton with AnyDynamoVertex.ofType[ET#SourceType] with AnyDynamoVertex,
  T <: Singleton with AnyDynamoVertex.ofType[ET#TargetType] with AnyDynamoVertex,
  STab <: Singleton with AnyVertexTable.ofType[ET#SourceType],
  TTab <: Singleton with AnyVertexTable.ofType[ET#TargetType]
](val source: S, val sourceTable: STab, val tpe: ET, val target: T,val targetTable: TTab) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
  type SourceTable = STab
  type TargetTable = TTab
}

object AnyDynamoEdge{
  type ofType[ET <: AnyEdgeType] = AnyDynamoEdge { type Tpe = ET }
}
