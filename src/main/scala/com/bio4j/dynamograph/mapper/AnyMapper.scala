package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.parser.SingleElement
import ohnosequences.tabula.AnyPutItemAction

trait AnyMapper {
  def map(element: SingleElement) : List[AnyPutItemAction]

}
