package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.AnyDynamoVertex
import com.bio4j.dynamograph.model.GeneralSchema.id
import scala.collection.JavaConversions._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import ohnosequences.typesets.TypeSet


class VertexReader[VT <: AnyDynamoVertex,R <: AnyRegion, As <: TypeSet, Rw <: TypeSet](val vertexType : VT ,val vertexTable : VertexTable[VT, R, As, Rw], val dbExecutor : AnyDynamoDbExecutor) extends AnyVertexReader{
  type VertexType = VT

  def read(identifier : id.Raw) : ReturnType = {
    val request = new GetItemRequest()
      .withTableName(vertexTable.tableName)
      .withKey(Map(id.label -> new AttributeValue().withS(identifier)))
    dbExecutor.execute(request)
  }
}
