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
import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._

class GoMapper extends AnyMapper {

  override def map(element: SingleElement): List[AnyPutItemAction] = {
	val values = element.vertexAttributes 
	val goTerm = GoTerm ->> (
	    GoTerm.raw(
	      GoTerm fields (
	          (id is values(id.label)) :~:
	          (name is values(name.label)) :~: 
	          (comment is values(comment.label)):~: 
	          (definition is values(definition.label)) :~:
	           ∅
	      ),""
	    )
      )
    // 
    val vertexItem = GoWriters.goTermVertexWriter.item ->> goTerm.fields

    val vertexId : String = goTerm.get(id)

    def toAnyPutItemAction(attributes: Map[String,String]): List[AnyPutItemAction] = {
      val rawEdge = Map(
        relationId.label -> (vertexId + attributes(targetId.label)), 
        sourceId.label   -> vertexId, 
        targetId.label   -> attributes(targetId.label)
      )
      writers(attributes(ParsingContants.relationType))(rawEdge)
    }
    
    GoWriters.goTermVertexWriter.write(vertexItem) ::: element.edges.map(toAnyPutItemAction).flatten
  }
  
  private def mapIsA(values : Map[String,String]) : List[AnyPutItemAction] = {
    val isARep = IsA ->> (
      IsA.raw(

        IsA fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
          ∅
          ),""
      )
    )
    GoWriters.isAEdgeWriter.write(isARep.fields)
  }
  private def mapHasPart(values : Map[String,String]) : List[AnyPutItemAction]  = {
    val hasPartRep = HasPart ->> (
      HasPart.raw(

        HasPart fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
           ∅
          ),""
      )
    )
    GoWriters.hasPartEdgeWriter.write(hasPartRep.fields)
  }
  private def mapPartOf(values : Map[String,String]) : List[AnyPutItemAction] = {
    val partOfRep = PartOf ->> (
      PartOf.raw(

        PartOf fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
          ∅
          ),""
      )
    )
    GoWriters.partOfEdgeWriter.write(partOfRep.fields)
  }
  private def mapRegulates(values : Map[String,String]) : List[AnyPutItemAction] = {
    val regulatesRep = Regulates ->> (
      Regulates.raw(

        Regulates fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
            ∅
          ),""
      )
    )
    GoWriters.regulatesEdgeWriter.write(regulatesRep.fields)
  }
  private def mapNegativelyRegulates(values : Map[String,String]) : List[AnyPutItemAction] = {
    val negativelyRegulatesRep = NegativelyRegulates ->> (
      NegativelyRegulates.raw(

        NegativelyRegulates fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
            ∅
          ),""
      )
    )
    GoWriters.negativelyRegulatesEdgeWriter.write(negativelyRegulatesRep.fields)
  }
  private def mapPositivelyRegulates(values : Map[String,String]) : List[AnyPutItemAction] = {
    val positivelyRegulatesRep = PositivelyRegulates ->> (
      PositivelyRegulates.raw(

        PositivelyRegulates fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
          ∅
          ),""
      )
    )
    GoWriters.positivelyRegulatesEdgeWriter.write(positivelyRegulatesRep.fields)
  }

  private def mapNamespace(values : Map[String,String]) : List[AnyPutItemAction] = {
    val namespaceRep = Namespace ->> (
      Namespace.raw(

        Namespace fields (
          (relationId is values(relationId.label)) :~:
          (sourceId is values(sourceId.label)) :~:
          (targetId is values(targetId.label)) :~:
           ∅
          ),""
      )
    )
    GoWriters.namespaceEdgeWriter.write(namespaceRep.fields)
  }

  val writers : Map[String, (Map[String,String]) => List[AnyPutItemAction]] = Map(
      IsAType.label			 		-> mapIsA _,
      HasPartType.label 			-> mapHasPart _,
      PartOfType.label 				-> mapPartOf _,
      RegulatesType.label			-> mapRegulates _,
      NegativelyRegulatesType.label -> mapNegativelyRegulates _,
      PositivelyRegulatesType.label -> mapPositivelyRegulates _, 
      NamespaceType.label 			-> mapNamespace _
   )

}
