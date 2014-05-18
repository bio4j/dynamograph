package com.bio4j.dynamograph.model.go.relationships

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.GOTermDAO
import com.bio4j.dynamograph.model.go.nodes.{GOTermType, GOTerm}

case object PositivelyRegulatesType extends ManyToMany (GOTermType, "negatively_regulates", GOTermType) {

}

case class PositivelyRegulatesImpl(
  val source: GOTerm.Rep,
  val target: GOTerm.Rep
)

case class PositivelyRegulates(val goTermDAO : GOTermDAO) extends Edge(PositivelyRegulatesType){ self =>

  type Raw = IsAImpl

  implicit object sourceGetter extends GetSource[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.source)
  }

  implicit object targetGetter extends GetTarget[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.target)
  }

}
