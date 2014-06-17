package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.scarph.AnyProperty.ReadFrom
import ohnosequences.scarph.SmthHasProperty.PropertyOf
import com.bio4j.dynamograph.dao.go.DynamoDbDao
import com.bio4j.dynamograph.model.GeneralSchema.id
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConverters._


trait AnyDynamoVertex extends AnyVertex { dynamoVertex =>

  final type Raw = Map[String, AttributeValue]

  val dao: DynamoDbDao = ServiceProvider.getDao()

  implicit def readFromDynamoVertex(vr: Rep) =
    new ReadFrom[Rep](vr) {
      def apply[P <: AnyProperty](p: P): p.Raw = vr.get(p.label).asInstanceOf[p.Raw]
    }

  implicit def unsafeGetProperty[P <: AnyProperty: PropertyOf[this.Tpe]#is](p: P) =
    new GetProperty[P](p) {
      def apply(rep: Rep): p.Raw = getValue(rep, p.label).asInstanceOf[p.Raw]
    }


  implicit def unsafeRetrieveOneOutEdge[E <: Singleton with AnyEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): RetrieveOutEdge[E] = new RetrieveOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep)).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeRetrieveManyOutEdge[E <: Singleton with AnyEdge {
    type Tpe <: From[dynamoVertex.Tpe] with ManyOut }](e: E): RetrieveOutEdge[E] = new RetrieveOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep)).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.toList: List[e.Rep]
    }
  }

  implicit def unsafeRetrieveOneInEdge[E <: Singleton with AnyEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): RetrieveInEdge[E] = new RetrieveInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep)).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeRetrieveManyInEdge[E <: Singleton with AnyEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): RetrieveInEdge[E] = new RetrieveInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep)).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.toList: List[e.Rep]
    }
  }

  private def getValue(rep: Rep, attributeName : String) : String = rep.get(attributeName).get.getS

  private def getId(rep: Rep) : String = getValue(rep, id.label)

}

class DynamoVertex[VT <: Singleton with AnyVertexType](val tpe: VT) extends AnyDynamoVertex {
  type Tpe = VT
}

