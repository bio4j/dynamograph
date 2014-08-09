package com.bio4j.dynamograph

import ohnosequences.typesets._
import ohnosequences.tabula._
import ohnosequences.scarph._
import ohnosequences.tabula.impl._
import com.bio4j.dynamograph.model.GeneralSchema._
import com.bio4j.dynamograph.model.VertexTable
import com.bio4j.dynamograph.model.EdgeTables

object testModel {

  // Integer, no Int
  case object testId extends Property[Integer]

  case object testName extends Property[String]

  
  val testEdgeAttributes = relationId :~: sourceId :~: targetId :~: ∅
  case object TestEdgeRecord              extends Record(testEdgeAttributes)
  object testEdgeType extends SealedEdgeType(
          testVertexType,  "TestEdgeType", TestEdgeRecord, testVertexType
  ) with ManyIn with ManyOut

  val testVertexAttributes = testId :~: testName :~: ∅
  case object TestVertexRecord            extends Record(testVertexAttributes)
  object testVertexType extends SealedVertexType("TestVertexType", TestVertexRecord)
  
  case object testVertex extends DynamoVertex(testVertexType)

  case object testEdge extends DynamoEdge(testVertex,testEdgeType,testVertex)

  // TODO id clashes with something, that's why this fails
  case object testVertexTable extends VertexTable(testVertexType, "TestVertex", EU)

  case object testEdgeTable extends EdgeTables(testVertexTable, testEdgeType, testVertexTable, "TestEdge", EU)

 }
