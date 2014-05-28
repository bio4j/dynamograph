package com.bio4j.dynamograph.dynamodb


trait AnyDynamoDbGraphCreator {
  def createEdge(dbModel: DynamoDbEdgeModel) : Boolean

  def createNode(dbModel : DynamoDbNodeModel) : Boolean

}
