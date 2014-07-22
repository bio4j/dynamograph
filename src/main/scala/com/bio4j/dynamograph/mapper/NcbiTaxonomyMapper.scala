package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.parser.SingleElement
import com.amazonaws.services.dynamodbv2.model.PutItemRequest


class NcbiTaxonomyMapper extends AnyMapper {
  override def map(element: SingleElement): List[PutItemRequest] = ???

}
