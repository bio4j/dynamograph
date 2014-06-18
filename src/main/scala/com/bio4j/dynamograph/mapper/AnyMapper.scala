package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.parser.SingleElement

trait AnyMapper {
  type ResultType
  def map(element: SingleElement) : ResultType

}
