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
  case class HasPart(override val dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, HasPartType, GOTermType)

  case class IsAType(override val dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, HasPartType, GOTermType)

  case class NegativelyRegulatesType(override val dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, HasPartType, GOTermType)

  case class PartOfType(override val dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, HasPartType, GOTermType)

  case class PositivelyRegulatesType(override val dbDao : DynamoDbDao) extends DynamoEdge(dbDao, GOTermType, HasPartType, GOTermType)

}
