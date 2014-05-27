package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue


case class DynamoRawVertex(id : DynamoElementIdentifier, attributes : Map[String,AttributeValue]){
  def getAttributeValue(name: String) = attributes.get(name).get.getS
}



