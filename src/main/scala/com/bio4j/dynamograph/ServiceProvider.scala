package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.IDynamoDbDao

object ServiceProvider extends IServiceProvider{

  def getDao() : IDynamoDbDao = ???

}
