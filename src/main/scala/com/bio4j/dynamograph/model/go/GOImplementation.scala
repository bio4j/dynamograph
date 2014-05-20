package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.dao.go.DynamoDbDao
import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.go.GOSchema._


object GOImplementation {

  // vertices
  case class GOTerm(override val dbDao : DynamoDbDao) extends DynamoVertex[GOTermType.type](dbDao, GOTermType) {

    implicit val _id : GetProperty[id.type] = unsafeGetProperty(id)

    implicit val _name : GetProperty[name.type] = unsafeGetProperty(name)

    implicit val _namespace : GetProperty[namespace.type] = unsafeGetProperty(namespace)

    implicit val _definition : GetProperty[definition.type] = unsafeGetProperty(definition)

    implicit val _comment : GetProperty[comment.type] = unsafeGetProperty(comment)
  }

  //edges
  case class HasPart(dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, HasPartType, GOTermType)

  case class IsA(dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, IsAType, GOTermType)

  case class NegativelyRegulates(dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, NegativelyRegulatesType, GOTermType)

  case class PartOf(dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, PartOfType, GOTermType)

  case class PositivelyRegulates(dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, PositivelyRegulatesType, GOTermType)

}
