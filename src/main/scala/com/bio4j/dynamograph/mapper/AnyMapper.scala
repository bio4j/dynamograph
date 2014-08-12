package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.parser.SingleElement
import ohnosequences.tabula.AnyPutItemAction
import com.amazonaws.services.dynamodbv2.model.PutItemRequest

trait AnyMapper {
  def map(element: SingleElement) : List[PutItemRequest]

}
