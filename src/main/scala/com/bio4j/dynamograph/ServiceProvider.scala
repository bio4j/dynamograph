package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor

object ServiceProvider extends AnyServiceProvider {

  def getDao() : AnyDynamoDbDao = ???

  def getDynamoDbExecutor() : AnyDynamoDbExecutor = ???

}
