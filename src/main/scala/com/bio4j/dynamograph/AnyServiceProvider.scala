package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao


trait AnyServiceProvider {
  def getDao() : AnyDynamoDbDao

}
