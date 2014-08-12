package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.typesets._
import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao
import com.bio4j.dynamograph.model.Properties._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConverters._


trait AnyDynamoVertex extends AnyVertex { dynamoVertex =>

  final type Raw = Map[String, AttributeValue]

  val dao: AnyDynamoDbDao = ServiceProvider.dao

  implicit def unsafeGetProperty[P <: AnyProperty: Property.Of[this.Tpe]#is](p: P) =
    new PropertyGetter[P](p) {
      def apply(rep: Rep): p.Raw = getValue(rep, p)
    }


  implicit def unsafeGetOneOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[E#Rep] = {
      val it = dao.getOutRelationships(getId(rep), e).asInstanceOf[List[E#Rep]]
      it.headOption: Option[E#Rep]
    }
  }

  implicit def unsafeGetManyOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with ManyOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[E#Rep] = {
      val it = dao.getOutRelationships(getId(rep),e).asInstanceOf[List[E#Rep]]
      it: List[E#Rep]
    }
  }

  implicit def unsafeGetOneInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[E#Rep] = {
      val it = dao.getOutRelationships(getId(rep),e).asInstanceOf[List[E#Rep]]
      it.headOption: Option[E#Rep]
    }
  }

  implicit def unsafeGetManyInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[E#Rep] = {
      val it = dao.getOutRelationships(getId(rep), e).asInstanceOf[List[E#Rep]]
      it.toList: List[E#Rep]
    }
  }

  private def getValue[P <: AnyProperty](rep: Rep, p : P ) : P#Value = rep.get(p.label).getOrElse(new AttributeValue().withS("")).getS.asInstanceOf[P#Value]

  private def getId(rep: Rep) : String = getValue(rep, id)

}

class DynamoVertex[VT <: Singleton with AnyVertexType](val tpe: VT) extends AnyDynamoVertex {
  type Tpe = VT
}

object AnyDynamoVertex{
  type ofType[VT <: AnyVertexType] = AnyDynamoVertex { type Tpe = VT }
}

