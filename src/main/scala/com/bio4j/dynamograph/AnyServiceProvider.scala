package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.AnyDynamoDbReadDao


trait AnyServiceProvider {
  def getDao() : AnyDynamoDbReadDao

}
