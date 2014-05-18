package com.bio4j.dynamograph.model.go.relationships

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.GOTermDAO
import com.bio4j.dynamograph.model.go.nodes.{GOTerm, GOTermType}


case object IsAType extends ManyToMany (GOTermType, "is_a", GOTermType) {

}

case class IsAImpl(
  val source: String,
  val target: String
)

case class IsA(val goTermDAO : GOTermDAO) extends Edge(IsAType){ self =>

  type Raw = IsAImpl

  implicit object sourceGetter extends GetSource[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.source)
  }

  implicit object targetGetter extends GetTarget[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.target)
  }

}
