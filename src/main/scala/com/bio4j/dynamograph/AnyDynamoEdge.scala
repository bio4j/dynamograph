package com.bio4j.dynamograph

import com.bio4j.dynamograph.model.AnyEdgeTables
import com.bio4j.dynamograph.reader.AnyEdgeReader
import ohnosequences.scarph._
import ohnosequences.typesets._
import com.bio4j.dynamograph.default._


trait AnyDynamoEdge extends AnyEdge { dynamoEdge =>


  final type Raw = Representation
  
  type Tpe <: Singleton with AnyEdgeTypeWithId
  val tpe : Tpe


  type Source <: AnyDynamoVertex.ofType[Tpe#SourceType] with AnyDynamoVertex
  val source: Source

  type Target <: AnyDynamoVertex.ofType[Tpe#TargetType] with AnyDynamoVertex
  val target: Target

  type Tables <: Singleton with AnyEdgeTables.withEdgeType[Tpe]
  val tables : Tables

  type Reader <: Singleton with AnyEdgeReader.withTableType[Tables]
  val reader: Reader


  implicit def unsafeGetProperty[P <: AnyProperty: Property.Of[this.Tpe]#is](p: P) =
    new PropertyGetter[P](p) {
      def apply(rep: Rep) : p.Raw = getValue(rep, p).asInstanceOf[p.Raw]
    }

  implicit object sourceGetter extends GetSource {
    def apply(rep: dynamoEdge.Rep): Out =
      source ->> source.reader.read(getValue(rep,tpe.sourceId))
  }

  implicit object targetGetter extends GetTarget {
    def apply(rep: dynamoEdge.Rep): Out =
      target ->> target.reader.read(getValue(rep,tpe.targetId))
  }

}

class DynamoEdge[
  ET <: Singleton with AnyEdgeTypeWithId,
  S <: Singleton with AnyDynamoVertex.ofType[ET#SourceType] with AnyDynamoVertex,
  T <: Singleton with AnyDynamoVertex.ofType[ET#TargetType] with AnyDynamoVertex,
  ETab <: Singleton with AnyEdgeTables.withEdgeType[ET],
  R <: Singleton with AnyEdgeReader.withTableType[ETab]
](
  val source: S,
  val tpe: ET,
  val target: T,
  val tables : ETab,
  val reader : R
) extends AnyDynamoEdge {
  type Source = S
  type Tpe = ET
  type Target = T
  type Tables = ETab
  type Reader = R
}

object AnyDynamoEdge{
  type ofType[ET <: Singleton with AnyEdgeTypeWithId] = AnyDynamoEdge { type Tpe = ET }
}
