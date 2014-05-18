package com.bio4j.dynamograph.model.go.relationships

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.GOTermDAO
import com.bio4j.dynamograph.model.go.nodes.{GOTermType, GOTerm}

case object PartOfType extends ManyToMany (GOTermType, "part_of", GOTermType) {

}

case class PartOfImpl(
  val source: String,
  val target: String
)

case class PartOf(val goTermDAO : GOTermDAO) extends Edge(PartOfType){ self =>
  type Raw = PartOfImpl

  implicit object sourceGetter extends GetSource[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.source)
  }

  implicit object targetGetter extends GetTarget[GOTerm.type](GOTerm) {
    def apply(rep: self.Rep) = GOTerm ->> goTermDAO.get(rep.target)
  }
}

