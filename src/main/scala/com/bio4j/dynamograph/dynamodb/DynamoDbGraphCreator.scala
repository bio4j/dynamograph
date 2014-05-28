package com.bio4j.dynamograph.dynamodb


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB


class DynamoDbGraphCreator(val ddb : AmazonDynamoDB) extends AnyDynamoDbGraphCreator{

  def createNode(dbModel : DynamoDbNodeModel) : Boolean = {
    ohnosequences.awstools.ddb.Utils.createTable(ddb,dbModel.nodeTableName,dbModel.hash,None ,dbModel.writeThroughput,dbModel.readThroughput,true)
  }
  
  def createEdge(dbModel: DynamoDbEdgeModel) : Boolean = {
    ohnosequences.awstools.ddb.Utils.createTable(ddb,dbModel.inEdgeTableName,dbModel.nodeId,Option(dbModel.relationId),dbModel.writeThroughput,dbModel.readThroughput,true)
    ohnosequences.awstools.ddb.Utils.createTable(ddb,dbModel.outEdgeTableName,dbModel.nodeId,Option(dbModel.relationId),dbModel.writeThroughput,dbModel.readThroughput,true)
    ohnosequences.awstools.ddb.Utils.createTable(ddb,dbModel.edgeTableName,dbModel.relationId,None,dbModel.writeThroughput,dbModel.readThroughput,true)
  }
  

}
