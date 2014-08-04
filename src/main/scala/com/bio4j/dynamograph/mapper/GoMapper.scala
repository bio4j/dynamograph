package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.model.go.GoImplementation._
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import com.amazonaws.services.dynamodbv2.model.{PutItemRequest, AttributeValue}
import com.bio4j.dynamograph.model.go.GoSchema._
import com.bio4j.dynamograph.writer.GoWriters
import ohnosequences.scarph.ops.default._
import ohnosequences.tabula._
import ohnosequences.typesets._
import ohnosequences.typesets.{AnyProperty, TypeSet}
import scala.annotation.tailrec
import com.bio4j.dynamograph.parser.SingleElement
import scala.Some
import shapeless._, poly._
import com.bio4j.dynamograph.parser.SingleElement
import scala.Some




class GoMapper extends AnyMapper {

  override def map(element: SingleElement): List[AnyPutItemAction] = {

    val vertexAttrs = element.vertexAttributes

    // TODO you don't need all this tagging, I'll fix it later
    case object valueMapper extends Poly1{
      implicit def caseN[A <: Singleton with AnyProperty.ofValue[Integer]] =
        at[A]{ a : A => (a ->> vertexAttrs(a.label).toInt.asInstanceOf[a.Raw]): A#Rep }
      implicit def caseS[A <: Singleton with AnyProperty.ofValue[String]] =
        at[A]{ a : A => (a ->> vertexAttrs(a.label).asInstanceOf[a.Raw]): A#Rep }
    }
    val value = GoTerm ->> (
      GoTerm.raw(
        GoTerm fields(GoTerm.tpe.record.properties.map(valueMapper)),"")
      )
    val vertexId : String = value.get(id)

    def toWriteOperation(attributes: Map[String,String]) : List[AnyPutItemAction]   = {
      val targetIdentifier : String = attrValue(attributes, targetId.label)
      val rawEdge = Map(
        relationId.label -> (vertexId + targetIdentifier), 
        sourceId.label   -> vertexId, 
        targetId.label   -> targetIdentifier
      ).mapValues(mapValue)
      createEdge(attrValue(attributes, ParsingContants.relationType), rawEdge)
    }

    GoWriters.goTermVertexWriter.write(value) ::: element.edges.map(toWriteOperation).flatten
  }

  private def mapValue(x : String) : AttributeValue = new AttributeValue().withS(x)

  // NOTE: I don't get smth here, but this `.get` doesn't look nice in general
  private def attrValue(attributes : Map[String, String], name : String) : String = attributes.get(name).get

  // NOTE: the general problem that appears here is that we want to _map a value to a type_.
  // i.e. you want to get a type depending on a value. and generally there is no good solution for this in Scala.
  private def createEdge(relationType: String, rawEdge: Map[String, AttributeValue]): List[AnyPutItemAction] = {
    GoWriters.edgeWritersMap.get(relationType) match {
      case None => List() // no writer were found for this label
      case Some(writer) => writer.write(writer.element ->> rawEdge)
    }
  }

}
