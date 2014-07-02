package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import ohnosequences.tabula.AnyDynamoDBService
import ohnosequences.tabula.impl.DynamoDBExecutors

trait AnyServiceProvider {

  def dao() : AnyDynamoDbDao

  def dynamoDbExecutor() : AnyDynamoDbExecutor
  
  def service() : AnyDynamoDBService

  def executors() : DynamoDBExecutors
  

}
