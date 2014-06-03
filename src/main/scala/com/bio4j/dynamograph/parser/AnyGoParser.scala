package com.bio4j.dynamograph.parser

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.io.Source

trait AnyGoParser {
  def parse(src : Source) : Map[String, List[(String, Any)]]
 }
