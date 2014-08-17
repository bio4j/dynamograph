package com.bio4j.dynamograph

import com.amazonaws.services.dynamodbv2.model.AttributeValue
import ohnosequences.typesets.AnyProperty


object default {

  type Representation = Map[String,AttributeValue]

  def getValue[P <: AnyProperty](rep: Representation, p : P ): P#Value = {
    val attr = rep.get(p.label).getOrElse(new AttributeValue().withS(""))
    Option(attr.getN) match {
      case Some(n) if !n.isEmpty => attr.getN.toInt.asInstanceOf[P#Value]
      case _ => attr.getS.asInstanceOf[P#Value]
    }
  }

}
