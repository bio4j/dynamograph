package com.bio4j.dynamograph

import ohnosequences.scarph.AnyEdgeType
import com.bio4j.dynamograph.model.go.GOSchema._
import com.bio4j.dynamograph.dynamodb.DynamoDbEdgeModel
import com.bio4j.dynamograph.dynamodb.model.DynamoDbGoSchema._


object EdgeTypeToTableModel {

  private val edgeTypeMapping : Map[AnyEdgeType, DynamoDbEdgeModel] = Map(IsAType -> isAEdge, HasPartType -> hasPartEdge,
    PartOfType -> partOfEdge,  PositivelyRegulatesType -> positivelyRegulatesEdge,
    NegativelyRegulatesType -> negativelyRegulatesEdge)

  def getEdgeTableModel(edgeType : AnyEdgeType) : Option[DynamoDbEdgeModel] = edgeTypeMapping.get(edgeType)

}
