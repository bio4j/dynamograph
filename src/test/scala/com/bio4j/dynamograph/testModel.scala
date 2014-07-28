package com.bio4j.dynamograph

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import ohnosequences.typesets._
import ohnosequences.tabula._
import ohnosequences.scarph._
import ohnosequences.tabula.impl._, ImplicitConversions._

object testModel {

  // Integer, no Int
  case object id extends Property[Integer]

  case object name extends Property[String]

  object testVertexType extends VertexType("TestVertexType")

  object testEdgeType extends ManyToMany (testVertexType, "testEdgeType", testVertexType)

  case object testVertex extends DynamoVertex(testVertexType)

  case object testEdge extends DynamoEdge(testVertex,testEdgeType,testVertex)

  // TODO id clashes with something, that's why this fails
  case object testVertexTable extends VertexTable(testVertex, "TestVertex", EU, id :~: ∅)

  case object testEdgeTable extends EdgeTables(testEdge, "TestVertex", EU)

 }
