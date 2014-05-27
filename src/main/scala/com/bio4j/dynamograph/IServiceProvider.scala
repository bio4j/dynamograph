package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.IDynamoDbDao


trait IServiceProvider {
  def getDao() : IDynamoDbDao

}
