package com.bio4j.dynamograph.mapper

import com.bio4j.dynamograph.model.go.GOImplementation.GOTerm
import com.bio4j.dynamograph.model.GeneralSchema.id
import com.bio4j.dynamograph.parser.SingleElement


class GOMapper extends AnyMapper{
  type ResultType = GOTerm.type

  override def map(element: SingleElement): GOMapper#ResultType = ???

}
