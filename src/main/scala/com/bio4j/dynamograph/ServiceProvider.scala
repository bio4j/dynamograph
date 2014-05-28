package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao

object ServiceProvider extends AnyServiceProvider{

  def getDao() : AnyDynamoDbDao = ???

}
