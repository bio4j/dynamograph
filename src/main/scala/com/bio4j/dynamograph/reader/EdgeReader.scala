package com.bio4j.dynamograph.reader

import com.typesafe.scalalogging.LazyLogging
import treelog.LogTreeSyntaxWithoutAnnotations._
import scalaz._
import Scalaz._
import com.bio4j.dynamograph.model._
import ohnosequences.tabula.AnyCompositeKeyTable
import com.amazonaws.services.dynamodbv2.model._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

trait AnyEdgeReader extends LazyLogging {
  type EdgeTables <: Singleton with AnyEdgeTables
  val edgeTables : EdgeTables
  
  val dbExecutor : AnyDynamoDbExecutor
  
  def readOut(vId : EdgeTables#EdgeType#Id#Value) : List[Map[String,AttributeValue]] = read(vId, edgeTables.outTable)

  def readIn(vId : EdgeTables#EdgeType#Id#Value) : List[Map[String,AttributeValue]] = read(vId, edgeTables.inTable)

  private def read(vId : EdgeTables#EdgeType#Id#Value, table : AnyCompositeKeyTable) : List[Map[String,AttributeValue]] = {
    val result = executeRead(vId, table).run
    logger.debug(result.written.shows)
    result.value.getOrElse(Nil)

  }

  private def executeRead(vId : EdgeTables#EdgeType#Id#Value, table : AnyCompositeKeyTable) = {
    "Executing read" ~< {
      for {
        hashKeyCondition <- prepareHashKeyCondition(vId)
        request <- prepareQueryRequest(table.name, table.hashKey.label, hashKeyCondition)
        requestResult <- dbExecutor.execute(request) ~> "Executing query request"
        tableKeys <- prepareTableKeys(table.hashKey.label, requestResult) ~> "Preparing table keys"
        batchRequest <- prepareBatchRequest(edgeTables.edgeTable.name, tableKeys)
        batchResult <- dbExecutor.execute(batchRequest) ~> "Executing batch request"
      } yield batchResult
    }
  }

  private def prepareTableKeys(linkingHashKeyName : String ,linkingTableRecords : List[Map[String,AttributeValue]]) = {
    for {
      r <- linkingTableRecords
      key <- r.get(linkingHashKeyName)
      keyToAttr <- Some(Map(edgeTables.edgeTable.hashKey.label -> key).asJava)
    } yield keyToAttr
  }

  private def prepareHashKeyCondition(vId : EdgeTables#EdgeType#Id#Value) = {
    "Preparing HashKeyCondition" ~< {
      for {
        operator <- ComparisonOperator.EQ ~> ("Operator: " + _)
        id <- vId ~> ("Vertex id = " + _)
        attributeList <- new AttributeValue().withS(vId) ~> ("Attribute list = " + _)
        hashCondition <- new Condition().withComparisonOperator(operator).withAttributeValueList(attributeList) ~> ("hashKeyCondition = " + _ )
      } yield hashCondition
    }
  }

  private def prepareQueryRequest(tableName : String, linkingHashKeyName : String, hashKeyCondition : Condition) = {
    "Preparing Query request" ~< {
      for {
        name <- tableName ~> ("Table name = " + _)
        hKeyName <- linkingHashKeyName ~> ("Linking Hash Key Name = " + _)
        condition <- hashKeyCondition ~> ("Hash key condition = " + _)
        mapCondition <- Map(hKeyName -> condition) ~> ("map condition = " + _)
        request <-  new QueryRequest().withTableName(name).withKeyConditions(mapCondition) ~> ("Query request =" + _)
      } yield request
    }
  }

  private def prepareBatchRequest(tableName : String, tableKeys : List[java.util.Map[String,AttributeValue]]) = {
    "Preparing batchRequest" ~< {
      for {
        name <- tableName ~> ("Table name = " + _)
        keys <- tableKeys ~> ("Table keys = " + _ )
        keysAndAttributes <- new KeysAndAttributes().withKeys(keys) ~> ("Keys and attributes = " + _)
        map <- Map(name -> keysAndAttributes) ~> ("Mapped keys and attributes = " + _)
      } yield new BatchGetItemRequest().withRequestItems(map)
    }
  }
}

object AnyEdgeReader{
  type withTableType[ET <: Singleton with AnyEdgeTables] = AnyEdgeReader{ type EdgeTables = ET}
}


abstract class EdgeReader[ET <: Singleton with AnyEdgeTables](val edgeTables : ET, override val dbExecutor : AnyDynamoDbExecutor) extends AnyEdgeReader {
  type EdgeTables = ET

}
