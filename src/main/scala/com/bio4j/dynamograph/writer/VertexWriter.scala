package com.bio4j.dynamograph.writer

import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import com.bio4j.dynamograph.model.AnyVertexTable
import com.typesafe.scalalogging.LazyLogging
import treelog.LogTreeSyntaxWithoutAnnotations._
import scalaz._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.bio4j.dynamograph.default._

trait AnyVertexWriter extends AnyWriter with LazyLogging {
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable : VertexTable

  def write(vertex: Representation): List[PutItemRequest] = {
    val result = preparePutItemRequest(vertex).run
    logger.debug(result.written.toString)
    result.value.getOrElse(Nil)
  }

  private def preparePutItemRequest(vertex: Representation) = {
    "Calculation of putItemRequest" ~< {
      for {
        tableName <- vertexTable.table.name ~> ("Table name = " + _)
        item <- vertex ~> ("Attributes = " + _)
        itemRequest <- new PutItemRequest().withTableName(vertexTable.table.name).withItem(vertex) ~> ("PutItemRequest = " + _)
      } yield List(itemRequest)
    }
  }
}

class VertexWriter[VT <: Singleton with AnyVertexTable](val vertexTable : VT) extends AnyVertexWriter {
  type VertexTable = VT
}
