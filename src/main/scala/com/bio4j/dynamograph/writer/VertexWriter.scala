package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable
import ohnosequences.tabula.AnyRegion
import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoVertex}
import ohnosequences.typesets.TypeSet
import ohnosequences.tabula.impl.ImplicitConversions._
import toSDKRep._
import fromSDKRep._
import shapeless.Poly1
import ohnosequences.tabula.impl.actions.InHashKeyTable
import ohnosequences.tabula.Active
import ohnosequences.tabula.ThroughputStatus
import ohnosequences.scarph.{AnySealedVertexType, AnySealedVertex}
import com.amazonaws.services.dynamodbv2.model.AttributeValue

trait AnyVertexWriter extends AnyWriter{
  type Element <: AnyDynamoVertex
}

class VertexWriter[V <: AnySealedVertexType, R <: AnyRegion]
  (val element: V, val vertexTable : VertexTable[V, R]) extends AnyVertexWriter {

  type Element = V

  def write(rep: vertexTable.vertexItem.Raw): List[WriteType] = {
    List(InHashKeyTable(vertexTable.table, Active(vertexTable.table, ServiceProvider.service.account,
      ThroughputStatus(1, 1))) putItem vertexTable.vertexItem withValue (vertexTable.vertexItem ->> rep))
  }
}
