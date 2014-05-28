package com.bio4j.dynamograph.dao.go

import com.bio4j.dynamograph.{DynamoElementIdentifier, DynamoRawEdge, DynamoRawVertex}
import com.bio4j.dynamograph.dynamodb.DynamoDbEdgeModel

trait AnyDynamoDbDao {
  def get(id : DynamoElementIdentifier) : Option[DynamoRawVertex]

  def getInRelationships(id : DynamoElementIdentifier, edgeModel: Option[DynamoDbEdgeModel]) : List[DynamoRawEdge]

  def getOutRelationships(id : DynamoElementIdentifier, edgeModel: Option[DynamoDbEdgeModel]) : List[DynamoRawEdge]

}
