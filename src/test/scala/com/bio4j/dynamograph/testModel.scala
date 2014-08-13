package com.bio4j.dynamograph

import com.bio4j.dynamograph.model.go._
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph._
import ohnosequences.typesets._
import ohnosequences.tabula._
import ohnosequences.scarph._
import ohnosequences.tabula.impl._, ImplicitConversions._
import com.bio4j.dynamograph.model._

object testModel {

  case object testId extends Property[String]

  case object edgeId extends Property[String]
  case object name extends Property[String]

  object TestVertexType       extends VertexTypeWithId(testId, "testVertexType")
  implicit val TestVertexType_id         = TestVertexType has testId

  case object TestEdgeType    extends EdgeTypeWithId (TestVertexType, sourceId, relationId, "testEdgeType", TestVertexType, targetId) 
  	with ManyIn with ManyOut

  case object TestVertexTable               extends VertexTable(TestVertexType, "TestVertex", EU)
  case object TestEdgeTables                extends EdgeTables(TestEdgeType, "TestEdge", EU)
  	
  case object TestVertex extends DynamoVertex(TestVertexType)

  case object testEdge extends DynamoEdge(TestVertex, TestVertexTable, TestEdgeType, TestVertex, TestVertexTable)

 }
