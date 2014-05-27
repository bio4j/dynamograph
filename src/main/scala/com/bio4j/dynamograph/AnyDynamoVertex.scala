package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.scarph.AnyProperty.ReadFrom
import ohnosequences.scarph.SmthHasProperty.PropertyOf
import scala.collection.JavaConverters._
import com.bio4j.dynamograph.dao.go.IDynamoDbDao


trait AnyDynamoVertex extends AnyVertex { dynamoVertex =>

  final type Raw = DynamoRawVertex

  val dao: IDynamoDbDao = ServiceProvider.getDao()

  implicit def readFromDynamoVertex(vr: Rep) =
    new ReadFrom[Rep](vr) {
      def apply[P <: AnyProperty](p: P): p.Raw = vr.getAttributeValue(p.label).asInstanceOf[p.Raw]
    }

  implicit def unsafeGetProperty[P <: AnyProperty: PropertyOf[this.Tpe]#is](p: P) =
    new GetProperty[P](p) {
      def apply(rep: Rep): p.Raw = rep.getAttributeValue(p.label).asInstanceOf[p.Raw]
    }


  implicit def unsafeRetrieveOneOutEdge[E <: Singleton with AnyEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): RetrieveOutEdge[E] = new RetrieveOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(rep.id).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeRetrieveManyOutEdge[E <: Singleton with AnyEdge {
    type Tpe <: From[dynamoVertex.Tpe] with ManyOut }](e: E): RetrieveOutEdge[E] = new RetrieveOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(rep.id).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.toList: List[e.Rep]
    }
  }

  implicit def unsafeRetrieveOneInEdge[E <: Singleton with AnyEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): RetrieveInEdge[E] = new RetrieveInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(rep.id).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeRetrieveManyInEdge[E <: Singleton with AnyEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): RetrieveInEdge[E] = new RetrieveInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(rep.id).asInstanceOf[java.lang.Iterable[e.Rep]].asScala
      it.toList: List[e.Rep]
    }
  }

}

class DynamoVertex[VT <: AnyVertexType](val tpe: VT) extends AnyDynamoVertex {
  type Tpe = VT
}

