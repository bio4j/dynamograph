package com.bio4j.dynamograph.model.go.relationships

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.GOTermDAO
import com.bio4j.dynamograph.model.go.nodes.{GOTermType, GOTerm}

case object NegativelyRegulatesType extends ManyToMany (GOTermType, "positively_regulates", GOTermType) {

}

case class NegativelyRegulatesImpl(
  val source: String,
  val target: String
)

case class NegativelyRegulates(val goTermDAO : GOTermDAO) extends Edge(NegativelyRegulatesType){ self =>

  type Raw = NegativelyRegulatesImpl

  implicit object sourceGetter extends GetSource[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.source)
  }

  implicit object targetGetter extends GetTarget[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.target)
  }

}
