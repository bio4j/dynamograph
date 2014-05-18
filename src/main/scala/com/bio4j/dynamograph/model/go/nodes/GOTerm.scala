package com.bio4j.dynamograph.model.go.nodes

import ohnosequences.scarph._
import com.bio4j.dynamograph.model.go.nodes.GOProperties._

case class GOTermImpl(
  val id: String,
  val name: String,
  val namespace: String,
  val definition: String,

  val synonyms: List[String],
  val crossRef: List[String],
  val subset: List[String],
  val comment: String
)

case object GOTerm extends Vertex(GOTermType) { self =>
  type Raw = GOTermImpl


  implicit object readId extends GetProperty(id) {
    def apply(rep: GOTerm.Rep): id.Raw = (rep: GOTerm.Rep).id
  }

  implicit object readName extends GetProperty(name) {
    def apply(rep: GOTerm.Rep): name.Raw = (rep: GOTerm.Rep).name
  }

  implicit object readNamespace extends GetProperty(namespace) {
    def apply(rep: GOTerm.Rep): namespace.Raw = GOTermNamespace.withName((rep: GOTerm.Rep).namespace)
  }

  implicit object readDefinition extends GetProperty(definition) {
    def apply(rep: GOTerm.Rep): definition.Raw = (rep: GOTerm.Rep).definition
  }

  implicit object readSynonyms extends GetProperty(synonyms) {
    def apply(rep: GOTerm.Rep): synonyms.Raw = (rep: GOTerm.Rep).synonyms
  }

  implicit object readCrossRef extends GetProperty(crossRef) {
    def apply(rep: GOTerm.Rep): crossRef.Raw = (rep: GOTerm.Rep).crossRef
  }

  implicit object readSubset extends GetProperty(subset) {
    def apply(rep: GOTerm.Rep): subset.Raw = (rep: GOTerm.Rep).subset
  }

  implicit object readComment extends GetProperty(comment) {
    def apply(rep: GOTerm.Rep): comment.Raw = (rep: GOTerm.Rep).comment
  }
}

object GOTermType extends VertexType("GOTerm"){
  implicit val termId         = this has id
  implicit val termName       = this has name
  implicit val termNamespace  = this has namespace
  implicit val termDefinition = this has definition

  // Optionals
  implicit val termSynonyms    = this has synonyms
  implicit val termCrossRef    = this has crossRef
  implicit val termSubset    = this has subset
  implicit val termComment    = this has comment

}


