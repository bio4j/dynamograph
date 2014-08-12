package com.bio4j.dynamograph

import ohnosequences.scarph._
import ohnosequences.typesets._
import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao
import com.bio4j.dynamograph.model.GeneralSchema.id
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConverters._


trait AnyDynamoVertex extends AnyVertex { dynamoVertex =>

  final type Raw = Map[String, AttributeValue]

  val dao: AnyDynamoDbDao = ServiceProvider.dao

  implicit def unsafeGetProperty[P <: AnyProperty: Property.Of[this.Tpe]#is](p: P) =
    new PropertyGetter[P](p) {
      def apply(rep: Rep): p.Raw = getValue(rep, p.label)
    }


  implicit def unsafeGetOneOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep), e).asInstanceOf[List[e.Rep]]
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeGetManyOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with ManyOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep),e).asInstanceOf[List[e.Rep]]
      it: List[e.Rep]
    }
  }

  implicit def unsafeGetOneInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep),e).asInstanceOf[List[e.Rep]]
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeGetManyInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(getId(rep), e).asInstanceOf[List[e.Rep]]
      it.toList: List[e.Rep]
    }
  }

  private def getValue[T](rep: Rep, attributeName : String) : T = rep.get(attributeName).getOrElse(new AttributeValue().withS("")).getS.asInstanceOf[T]

  private def getId(rep: Rep) : String = getValue(rep, id.label)

}

class DynamoVertex[VT <: Singleton with AnyVertexType](val tpe: VT) extends AnyDynamoVertex {
  type Tpe = VT
}

object AnyDynamoVertex{
  type ofType[VT <: AnyVertexType] = AnyDynamoVertex { type Tpe = VT }
}

