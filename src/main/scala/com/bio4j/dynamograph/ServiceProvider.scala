package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.AnyDynamoDbReadDao

object ServiceProvider extends AnyServiceProvider{

  def getDao() : AnyDynamoDbReadDao = ???
}
