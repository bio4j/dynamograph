package com.bio4j.dynamograph.dynamodb


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB


class DynamoDbGraphCreator(val ddb : AmazonDynamoDB) extends AnyDynamoDbGraphCreator{

  def createNode(dbModel : DynamoDbNodeModel) : Boolean = {
    val tableName = s"${dbModel.nodeName}Node"
    ohnosequences.awstools.ddb.Utils.createTable(ddb,tableName,dbModel.hash,None ,dbModel.writeThroughput,dbModel.readThroughput,true)
  }
  
  def createEdge(dbModel: DynamoDbEdgeModel) : Boolean = {
    val inTableName = s"In${dbModel.edgeName}Edge"
    val outTableName = s"Out${dbModel.edgeName}Edge"
    val tableName = s"${dbModel.edgeName}Edge"

    ohnosequences.awstools.ddb.Utils.createTable(ddb,inTableName,dbModel.nodeId,Option(dbModel.relationId),dbModel.writeThroughput,dbModel.readThroughput,true)
    ohnosequences.awstools.ddb.Utils.createTable(ddb,outTableName,dbModel.nodeId,Option(dbModel.relationId),dbModel.writeThroughput,dbModel.readThroughput,true)
    ohnosequences.awstools.ddb.Utils.createTable(ddb,tableName,dbModel.relationId,None,dbModel.writeThroughput,dbModel.readThroughput,true)
  }
  

}
