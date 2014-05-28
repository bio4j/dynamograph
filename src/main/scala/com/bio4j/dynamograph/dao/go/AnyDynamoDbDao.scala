package com.bio4j.dynamograph.dao.go

import com.bio4j.dynamograph.{DynamoElementIdentifier, DynamoRawEdge, DynamoRawVertex}

trait AnyDynamoDbDao {
  def get(id : DynamoElementIdentifier) : Option[DynamoRawVertex]

  def getInRelationships(id : DynamoElementIdentifier) : List[DynamoRawEdge]

  def getOutRelationships(id : DynamoElementIdentifier) : List[DynamoRawEdge]

}
