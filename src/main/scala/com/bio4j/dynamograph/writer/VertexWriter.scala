package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoVertex}
import ohnosequences.typesets.TypeSet
import ohnosequences.tabula.impl.ImplicitConversions._
import toSDKRep._
import fromSDKRep._
import ohnosequences.tabula.impl.actions.InHashKeyTable
import ohnosequences.tabula.Active
import ohnosequences.tabula.ThroughputStatus
import com.amazonaws.services.dynamodbv2.model.PutItemRequest

trait AnyVertexWriter extends AnyWriter{
  type Element <: AnyDynamoVertex
}

class VertexWriter[V <: AnyDynamoVertex, R <: AnyRegion, As <:TypeSet, Rw <: TypeSet]
  (val element: V, val vertexTable : VertexTable[V, R, As, Rw]) extends AnyVertexWriter {

  type Element = V

  def write(vertex: Element#Rep): List[PutItemRequest] = {
    return List(new PutItemRequest().withTableName(vertexTable.tableName).withItem(vertex))
  }
}
