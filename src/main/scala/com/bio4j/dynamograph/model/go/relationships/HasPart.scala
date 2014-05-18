package com.bio4j.dynamograph.model.go.relationships

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.GOTermDAO
import com.bio4j.dynamograph.model.go.nodes.{GOTerm, GOTermType}

case object HasPartType extends ManyToMany (GOTermType, "has_part", GOTermType) {

}

case class HasPartImpl(
  val source: String,
  val target: String
)

case class HasPart(val goTermDAO : GOTermDAO) extends Edge(HasPartType){ self =>

  type Raw = HasPartImpl

  implicit object sourceGetter extends GetSource[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.source)
  }

  implicit object targetGetter extends GetTarget[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.target)
  }
}


