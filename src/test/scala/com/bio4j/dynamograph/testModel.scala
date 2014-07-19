package com.bio4j.dynamograph

import ohnosequences.tabula.{EU, Attribute}
import ohnosequences.scarph.{ManyToMany, VertexType}
import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import com.bio4j.dynamograph.model.go.GoImplementation.GoTerm
import ohnosequences.typesets._

object testModel {

  case object id extends Attribute[Int]

  case object name extends Attribute[String]

  object testVertexType extends VertexType("TestVertexType")

  object testEdgeType extends ManyToMany (testVertexType, "testEdgeType", testVertexType)

  case object testVertex extends DynamoVertex(testVertexType)

  case object testEdge extends DynamoEdge(testVertex,testEdgeType,testVertex)

  case object testVertexTable extends VertexTable(testVertex, "TestVertex", EU, id :~: ∅)

  case object testEdgeTable extends EdgeTables(testEdge, "TestVertex", EU, id :~: ∅)

 }
