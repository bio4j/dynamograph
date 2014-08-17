package com.bio4j.dynamograph.writer

import ohnosequences.scarph._
import com.bio4j.dynamograph.default._
import com.amazonaws.services.dynamodbv2.model.PutItemRequest
import ohnosequences.tabula.AnyPutItemAction
import com.amazonaws.services.dynamodbv2.model.AttributeValue


trait AnyWriter {
  def write(rep: Representation): List[PutItemRequest]
}

