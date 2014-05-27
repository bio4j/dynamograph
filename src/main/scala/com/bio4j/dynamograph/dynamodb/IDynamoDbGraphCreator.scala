package com.bio4j.dynamograph.dynamodb


trait IDynamoDbGraphCreator {
  def createEdge(dbModel: DynamoDbEdgeModel) : Boolean

  def createNode(dbModel : DynamoDbNodeModel) : Boolean

}
