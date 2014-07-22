package com.bio4j.dynamograph.parser


case class SingleElement(val vertexType : String, val vertexAttributes: Map[String,String], val edges: List[Map[String,String]])
