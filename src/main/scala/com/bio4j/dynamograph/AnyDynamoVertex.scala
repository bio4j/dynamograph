package com.bio4j.dynamograph

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao
import com.bio4j.dynamograph.model.GeneralSchema.id
import scala.collection.JavaConverters._
import ohnosequences.tabula.AnyItem
import ohnosequences.tabula.AnyItem._
import ohnosequences.typesets._


trait AnyDynamoVertex extends AnySealedVertex { dynamoVertex =>

  val dao: AnyDynamoDbDao = ServiceProvider.dao

  type Other = String

  implicit def unsafeGetOneOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit isThere: id.type ∈ dynamoVertex.tpe.record.Properties, lookup: Lookup[dynamoVertex.tpe.record.Values, id.Entry]): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(rep.get(id), e).asInstanceOf[List[e.Rep]]
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeGetManyOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with ManyOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit isThere: id.type ∈ dynamoVertex.tpe.record.Properties, lookup: Lookup[dynamoVertex.tpe.record.Values, id.Entry]): e.tpe.Out[e.Rep] = {
      val it = dao.getOutRelationships(rep.get(id),e).asInstanceOf[List[e.Rep]]
      it: List[e.Rep]
    }
  }

  implicit def unsafeGetOneInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit isThere: id.type ∈ dynamoVertex.tpe.record.Properties, lookup: Lookup[dynamoVertex.tpe.record.Values, id.Entry]): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(rep.get(id),e).asInstanceOf[List[e.Rep]]
      it.headOption: Option[e.Rep]
    }
  }

  implicit def unsafeGetManyInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit isThere: id.type ∈ dynamoVertex.tpe.record.Properties, lookup: Lookup[dynamoVertex.tpe.record.Values, id.Entry]): e.tpe.In[e.Rep] = {
      val it = dao.getOutRelationships(rep.get(id), e).asInstanceOf[List[e.Rep]]
      it.toList: List[e.Rep]
    }
  }
}

class DynamoVertex[VT <: Singleton with AnySealedVertexType](val tpe: VT) extends AnyDynamoVertex {
  type Tpe = VT
}

object AnyDynamoVertex{
  type ofType[VT <: Singleton with AnySealedVertexType] = AnyDynamoVertex { type Tpe = VT }
}


