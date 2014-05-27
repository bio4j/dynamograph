package com.bio4j.dynamograph.dao.go

import com.bio4j.dynamograph.{DynamoRawEdge, DynamoRawVertex}

trait IDynamoDbDao {
  def get(id : String) : DynamoRawVertex

  def getInRelationships(id : String) : List[DynamoRawEdge]

  def getOutRelationships(id : String) : List[DynamoRawEdge]

}
