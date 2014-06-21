package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.DynamoDbDao

object ServiceProvider extends AnyServiceProvider {

  def getDao() : DynamoDbDao = ???

}
