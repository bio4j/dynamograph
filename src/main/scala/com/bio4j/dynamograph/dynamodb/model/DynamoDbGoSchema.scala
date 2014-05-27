package com.bio4j.dynamograph.dynamodb.model

import com.bio4j.dynamograph.dynamodb.{DynamoDbNodeModel, DynamoDbEdgeModel}


object DynamoDbGoSchema {

  val goTermNode = DynamoDbNodeModel("GoTerm")

  val isAEdge = DynamoDbEdgeModel("GoIsA")
  val hasPartEdge = DynamoDbEdgeModel("GoHasPart")
  val partOfEdge = DynamoDbEdgeModel("GoPartOf")
  val positivelyRegulatesEdge = DynamoDbEdgeModel("GoPositivelyRegulates")
  val negativelyRegulatesEdge = DynamoDbEdgeModel("GoNegativelyRegulates")

  def getEdges: List[DynamoDbEdgeModel] = List(isAEdge, hasPartEdge, partOfEdge, positivelyRegulatesEdge, negativelyRegulatesEdge)

  def getNodes: List[DynamoDbNodeModel] = List(goTermNode)

}
