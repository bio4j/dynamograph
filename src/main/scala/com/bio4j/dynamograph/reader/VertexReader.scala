package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.typesafe.scalalogging.LazyLogging
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import com.bio4j.dynamograph.model.AnyVertexTable
import treelog.LogTreeSyntaxWithoutAnnotations._
import scalaz._

trait AnyVertexReader extends LazyLogging {
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable : VertexTable
  
  val dbExecutor : AnyDynamoDbExecutor
  
  def read(identifier : VertexTable#VertexType#Id#Value) : Map[String,AttributeValue] = {
      val result = executeRead(identifier).run
      logger.debug(result.written.toString)
      result.value.getOrElse(Map())
  }

  private def executeRead(identifier : VertexTable#VertexType#Id#Value) = {
    "Executing vertex read operation" ~< {
      for {
        request <- createRequest(identifier) ~> ("Creating GetItemRequest")
        result <- dbExecutor.execute(request) ~> ("Executing GetItemRequest")
      } yield result
    }
  }

  private def createRequest(identifier : VertexTable#VertexType#Id#Value) = {
    for {
      id <- identifier ~> ("Vertex id = " + _)
      name <- vertexTable.table.name ~> ("Table name = " + _)
      hKey <- vertexTable.vertexType.id.label ~> ("Hash key name = " + _)
      keyCondition <- Map(hKey -> new AttributeValue().withS(id)) ~> ("Hash key condition = " + _)
      request <- new GetItemRequest().withTableName(name).withKey(keyCondition) ~> ("GetItemRequest = " + _)
    } yield request
  }
}


object AnyVertexReader{
  type withTableType[VT <: Singleton with AnyVertexTable] = AnyVertexReader {type VertexTable = VT}
}

class VertexReader[VT <: Singleton with AnyVertexTable](val vertexTable : VT, override val dbExecutor : AnyDynamoDbExecutor) extends AnyVertexReader{
  type VertexTable = VT
}
