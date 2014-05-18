package com.bio4j.dynamograph

import ohnosequences.scarph._
import java.util.Map
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.scarph.AnyProperty.ReadFrom
import ohnosequences.scarph.SmthHasProperty.PropertyOf
import ohnosequences.scarph.titan.AnyTEdge
import com.bio4j.dynamograph.dao.go.DynamoDbDao
import scala.collection.JavaConverters._


trait AnyDynamoVertex extends AnyVertex { dynamoVertex =>

  val dao : DynamoDbDao

  final type Raw = Map[String,AttributeValue]

  implicit def readFromDynamoVertex(vr: Rep) =
    new ReadFrom[Rep](vr) {
      def apply[P <: AnyProperty](p: P): p.Raw = vr.get(p.label).getS.asInstanceOf[p.Raw]
    }

  implicit def unsafeGetProperty[P <: AnyProperty: PropertyOf[this.Tpe]#is](p: P) =
    new GetProperty[P](p) {
      def apply(rep: Rep): p.Raw = rep.get(p.label).getS.asInstanceOf[p.Raw]
    }


  implicit def unsafeRetrieveOneOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): RetrieveOutEdge[E] = new RetrieveOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {

      val it = dao.getOutRelationships(rep.get("id").getS).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeRetrieveManyOutEdge[
  E <: Singleton with AnyTEdge { type Tpe <: From[dynamoVertex.Tpe] with ManyOut }
  ](e: E): RetrieveOutEdge[E] = new RetrieveOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(rep.get("id").getS).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.toList: List[e.Rep]
    }
  }

  implicit def unsafeRetrieveOneInEdge[
  E <: Singleton with AnyTEdge { type Tpe <: To[dynamoVertex.Tpe] with OneIn }
  ](e: E): RetrieveInEdge[E] = new RetrieveInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(rep.get("id").getS).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeRetrieveManyInEdge[
  E <: Singleton with AnyTEdge { type Tpe <: To[dynamoVertex.Tpe] with ManyIn }
  ](e: E): RetrieveInEdge[E] = new RetrieveInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(rep.get("id").getS).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.toList: List[e.Rep]
    }
  }

}

class DynamoVertex[VT <: AnyVertexType](val dbDao : DynamoDbDao,val tpe: VT) extends AnyDynamoVertex {
  type Tpe = VT
  val dao = dbDao
}

