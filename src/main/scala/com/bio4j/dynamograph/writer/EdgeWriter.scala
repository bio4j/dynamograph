package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.default._
import com.typesafe.scalalogging.LazyLogging
import treelog.LogTreeSyntaxWithoutAnnotations._
import scalaz._
import Scalaz._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.AnyEdgeTables
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._



trait AnyEdgeWriter extends AnyWriter with LazyLogging {
  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables : EdgeTables
  
  def write(edge: Representation): List[PutItemRequest] = {
    val result = prepareWriteResults(edge).run
    logger.debug(result.written.toString)
    result.value.getOrElse(Nil)
  }

  private def prepareWriteResults(edge: Representation) : DescribedComputation[List[PutItemRequest]] = {
    "Prepare PutItemRequests for Edge" ~< {
      for {
        inAttrs <- prepareAttributes(edge,edgeTables.inTable.hashKey.label, edgeTables.inTable.rangeKey.label) ~> ("Calculating attributes for inTable")
        outAttrs <- prepareAttributes(edge,edgeTables.outTable.hashKey.label, edgeTables.outTable.rangeKey.label) ~> ("Calculating attributes for outTable")
        requests <- preparePutItemRequests(edge, inAttrs, outAttrs)
      } yield requests
    }
  }

  private def prepareAttributes(relationAttrs : Representation, hashKey : String, rangeKey : String) = {
    for {
      items <- relationAttrs ~> ("Relation attributes = " + _)
      hKey <- hashKey ~> ("HashKey name = " + _)
      hKeyValue <- items(hKey) ~> ("HashKey value = " + _)
      rKey <- rangeKey ~> ("Range key name = " + _)
      rKeyValue <- items(rKey) ~> ("Range key Value = " + _)
      attributes <- Map(hKey -> hKeyValue, rKey -> rKeyValue) ~> ("Table attributes = " + _)
    } yield attributes
  }

  private def preparePutItemRequests(edge: Representation, inAttrs: Map[String,AttributeValue], outAttrs: Map[String,AttributeValue]) = {
    ("Calculating requests for in, out end edge tables") ~< {
      for {
        inRequest <- preparePutItemRequest(edgeTables.inTable.name, inAttrs)
        outRequest <- preparePutItemRequest(edgeTables.outTable.name, outAttrs)
        edgeRequest <- preparePutItemRequest(edgeTables.edgeTable.name, edge)
      } yield List(inRequest, outRequest, edgeRequest)
    }
  }

  private def preparePutItemRequest(tableName : String, items : Map[String,AttributeValue]) = {
    ("Creating PutItemRequest for " + tableName) ~< {
      for {
        name <- tableName ~> ("Table name = " + _)
        attributes <- items ~> ("Attributes = " + _)
      } yield new PutItemRequest().withTableName(name).withItem(attributes)
    }
  }
}

class EdgeWriter[ET <: Singleton with AnyEdgeTables](val edgeTables: ET) extends AnyEdgeWriter{

  type EdgeTables = ET

}

