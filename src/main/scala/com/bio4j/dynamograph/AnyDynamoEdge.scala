package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import ohnosequences.scarph.SmthHasProperty._
import com.bio4j.dynamograph.dao.go.{AnyDynamoDbDao, DynamoDbDao}
import com.bio4j.dynamograph.model.GeneralSchema._


trait AnyDynamoEdge extends AnyEdge { dynamoEdge =>


  final type Raw = Map[String, AttributeValue]

  val dao: AnyDynamoDbDao = ServiceProvider.getDao()

  type Source <: AnyVertex.ofType[Tpe#SourceType] with AnyDynamoVertex
  val source: Source

  type Target <: AnyVertex.ofType[Tpe#TargetType] with AnyDynamoVertex
  val target: Target


  implicit def unsafeGetProperty[P <: AnyProperty: PropertyOf[this.Tpe]#is](p: P) =
    new GetProperty[P](p) {
      def apply(rep: dynamoEdge.Rep): p.Raw = getValue(rep, p.label).asInstanceOf[p.Raw]
    }

  implicit object sourceGetter extends GetSource[Source](source) {
    def apply(rep: dynamoEdge.Rep): source.Rep =
      source ->> dao.get(getValue(rep,sourceId.label), source.tpe)
  }

  implicit object targetGetter extends GetTarget[Target](target) {
    def apply(rep: dynamoEdge.Rep): target.Rep =
      target ->> dao.get(getValue(rep,targetId.label), target.tpe)
  }

  private def getValue(rep: Rep, attributeName : String) : String = rep.get(attributeName).get.getS

}

class DynamoEdge[
ET <: AnyEdgeType,
S <: Singleton with AnyVertex.ofType[ET#SourceType] with AnyDynamoVertex,
T <: Singleton with AnyVertex.ofType[ET#TargetType] with AnyDynamoVertex
](val source: S, val tpe: ET, val target: T) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
}
