package com.bio4j.dynamograph

import com.bio4j.dynamograph.reader.AnyVertexReader
import ohnosequences.scarph._
import ohnosequences.typesets._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConverters._
import com.bio4j.dynamograph.model.AnyVertexTable


trait AnyDynamoVertex extends AnyVertex { dynamoVertex =>

  final type Raw = Map[String, AttributeValue]
  
  type Tpe <: Singleton with AnyVertexTypeWithId
  val tpe : Tpe
  
  type Table <: Singleton with AnyVertexTable.withVertexType[Tpe]
  val table : Table

  type Reader <: Singleton with AnyVertexReader.withTableType[Table]
  val reader : Reader

  implicit def unsafeGetProperty[P <: AnyProperty: Property.Of[this.Tpe]#is](p: P) =
    new PropertyGetter[P](p) {
      def apply(rep: Rep): p.Raw = getValue(rep, p)
    }


  implicit def unsafeGetOneOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with OneOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[E#Rep] = {
      val it = e.reader.readOut(getId(rep)).asInstanceOf[List[E#Rep]]
      it.headOption: Option[E#Rep]
    }
  }

  implicit def unsafeGetManyOutEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: From[dynamoVertex.Tpe] with ManyOut }](e: E): GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.Out[E#Rep] = {
      val it = e.reader.readOut(getId(rep)).asInstanceOf[List[E#Rep]]
      it: List[E#Rep]
    }
  }

  implicit def unsafeGetOneInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[E#Rep] = {
      val it = e.reader.readIn(getId(rep)).asInstanceOf[List[E#Rep]]
      it.headOption: Option[E#Rep]
    }
  }

  implicit def unsafeGetManyInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep): e.tpe.In[E#Rep] = {
      val it = e.reader.readIn(getId(rep)).asInstanceOf[List[E#Rep]]
      it.toList: List[E#Rep]
    }
  }

  private def getValue[P <: AnyProperty](rep: Rep, p : P ) : P#Value = rep.get(p.label).getOrElse(new AttributeValue().withS("")).getS.asInstanceOf[P#Value]

  private def getId(rep: Rep) : Tpe#Id#Value = getValue(rep, tpe.id)

}

class DynamoVertex[
  VT <: Singleton with AnyVertexTypeWithId,
  VTab <: Singleton with AnyVertexTable.withVertexType[VT],
  R <: Singleton with AnyVertexReader.withTableType[VTab]
](val tpe: VT, val table : VTab, val reader: R) extends AnyDynamoVertex {
  type Tpe = VT
  type Table = VTab
  type Reader = R
}

object AnyDynamoVertex{
  type ofType[VT <: Singleton with AnyVertexTypeWithId] = AnyDynamoVertex { type Tpe = VT }
}

