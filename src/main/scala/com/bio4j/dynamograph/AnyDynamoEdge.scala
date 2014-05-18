package com.bio4j.dynamograph

import java.util.Map
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph._
import ohnosequences.scarph.SmthHasProperty._
import com.bio4j.dynamograph.dao.go.DynamoDbDao


trait AnyDynamoEdge extends AnyEdge { dynamoEdge =>

  val dao : DynamoDbDao

  final type Raw = Map[String,AttributeValue]

  type Source <: AnyVertex.ofType[Tpe#SourceType] with AnyDynamoVertex
  val source: Source

  type Target <: AnyVertex.ofType[Tpe#TargetType] with AnyDynamoVertex
  val target: Target


  implicit def unsafeGetProperty[P <: AnyProperty: PropertyOf[this.Tpe]#is](p: P) =
    new GetProperty[P](p) {
      def apply(rep: Rep): p.Raw = rep.get(p.label).getS.asInstanceOf[p.Raw]
    }

  implicit object sourceGetter extends GetSource[Source](source) {
    def apply(rep: dynamoEdge.Rep): source.Rep =
      source ->> dao.get(rep.get("source").getN)
  }

  implicit object targetGetter extends GetTarget[Target](target) {
    def apply(rep: dynamoEdge.Rep): target.Rep =
      target ->> dao.get(rep.get("target").getN)
  }

}

class DynamoEdge[
ET <: AnyEdgeType,
S <: AnyVertex.ofType[ET#SourceType] with AnyDynamoVertex,
T <: AnyVertex.ofType[ET#TargetType] with AnyDynamoVertex
](val dbDao : DynamoDbDao, val source: S, val tpe: ET, val target: T) extends AnyDynamoEdge {
  val dao = dbDao
  type Source = S
  type Tpe = ET
  type Target = T
}
