package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.DynamoDbDao

trait AnyServiceProvider {

  def getDao() : DynamoDbDao

}
