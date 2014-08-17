package com.bio4j.dynamograph.parser.go

import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.go.GoSchema.{GoTermType, NamespaceType, IsAType}
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import scala.io.Source
import com.bio4j.dynamograph.parser.ParsingContants
import com.bio4j.dynamograph.parser.SingleElement


class PullGoParserTest extends Specification {

  "PullGoParser" should {
    "return singleElement" in new context{
      val underTest = new PullGoParser(Source.fromString(file))
      underTest.foreach(result += _)
      result must have size 1
    }

    "correctly parse vertex" in new context{
      val underTest = new PullGoParser(Source.fromString(file))
      underTest.foreach(result += _)
      val vertex = result.head.vertexAttributes
      vertex(id.label) must beEqualTo("GO:0000001")
      vertex(name.label) must beEqualTo("mitochondrion inheritance")
      vertex(ParsingContants.vertexType) must beEqualTo(GoTermType.label)
      vertex(definition.label) must beEqualTo("The distribution of mitochondria, including the mitochondrial genome, into daughter cells after mitosis or meiosis, mediated by interactions between mitochondria and the cytoskeleton.")
    }

    "return correct number of edges" in new context{
      val underTest = new PullGoParser(Source.fromString(file))
      underTest.foreach(result += _)
      val edges = result.head.edges
      edges must have size 3
    }

    "correctly parse edges" in new context{
      val underTest = new PullGoParser(Source.fromString(file))
      underTest.foreach(result += _)
      val edges = result.head.edges
      edges must containTheSameElementsAs(expectedEdgeValues)
    }

    "do not return empty map for unknown relation type" in new context{
      val underTest = new PullGoParser(Source.fromString(file2))
      underTest.foreach(result += _)
      val edges = result.head.edges
      edges must not contain(Map[String,String]())
    }
  }

  trait context extends Scope {

    val result = scala.collection.mutable.MutableList[SingleElement]()

    val expectedEdgeValues = List(
      Map(ParsingContants.relationType -> IsAType.label, targetId.label -> "GO:0048308"),
      Map(ParsingContants.relationType -> IsAType.label, targetId.label -> "GO:0048311"),
      Map(ParsingContants.relationType -> NamespaceType.label, targetId.label -> "biological_process")
    )

    val file =  """<?xml version="1.0"?>
                  |<owl:Class rdf:about="http://purl.obolibrary.org/obo/GO_0000001">
                  |        <rdfs:label rdf:datatype="http://www.w3.org/2001/XMLSchema#string">mitochondrion inheritance</rdfs:label>
                  |        <rdfs:subClassOf rdf:resource="http://purl.obolibrary.org/obo/GO_0048308"/>
                  |        <rdfs:subClassOf rdf:resource="http://purl.obolibrary.org/obo/GO_0048311"/>
                  |        <oboInOwl:id rdf:datatype="http://www.w3.org/2001/XMLSchema#string">GO:0000001</oboInOwl:id>
                  |        <obo:IAO_0000115 rdf:datatype="http://www.w3.org/2001/XMLSchema#string">The distribution of mitochondria, including the mitochondrial genome, into daughter cells after mitosis or meiosis, mediated by interactions between mitochondria and the cytoskeleton.</obo:IAO_0000115>
                  |        <oboInOwl:hasOBONamespace rdf:datatype="http://www.w3.org/2001/XMLSchema#string">biological_process</oboInOwl:hasOBONamespace>
                  |        <oboInOwl:hasExactSynonym rdf:datatype="http://www.w3.org/2001/XMLSchema#string">mitochondrial inheritance</oboInOwl:hasExactSynonym>
                  |    </owl:Class>
                  |    <owl:Axiom>
                  |        <oboInOwl:hasDbXref rdf:datatype="http://www.w3.org/2001/XMLSchema#string">GOC:mcc</oboInOwl:hasDbXref>
                  |        <oboInOwl:hasDbXref rdf:datatype="http://www.w3.org/2001/XMLSchema#string">PMID:10873824</oboInOwl:hasDbXref>
                  |        <oboInOwl:hasDbXref rdf:datatype="http://www.w3.org/2001/XMLSchema#string">PMID:11389764</oboInOwl:hasDbXref>
                  |        <owl:annotatedTarget rdf:datatype="http://www.w3.org/2001/XMLSchema#string">The distribution of mitochondria, including the mitochondrial genome, into daughter cells after mitosis or meiosis, mediated by interactions between mitochondria and the cytoskeleton.</owl:annotatedTarget>
                  |        <owl:annotatedSource rdf:resource="http://purl.obolibrary.org/obo/GO_0000001"/>
                  |        <owl:annotatedProperty rdf:resource="http://purl.obolibrary.org/obo/IAO_0000115"/>
                  |    </owl:Axiom> """.stripMargin

    val file2 =  """<?xml version="1.0"?>
                      <owl:Class rdf:about="http://purl.obolibrary.org/obo/GO_0000018">
                  |        <rdfs:label rdf:datatype="http://www.w3.org/2001/XMLSchema#string">regulation of DNA recombination</rdfs:label>
                  |        <owl:equivalentClass>
                  |            <owl:Class>
                  |                <owl:intersectionOf rdf:parseType="Collection">
                  |                    <rdf:Description rdf:about="http://purl.obolibrary.org/obo/GO_0065007"/>
                  |                    <owl:Restriction>
                  |                        <owl:onProperty rdf:resource="http://purl.obolibrary.org/obo/RO_1002211"/>
                  |                        <owl:someValuesFrom rdf:resource="http://purl.obolibrary.org/obo/GO_0006310"/>
                  |                    </owl:Restriction>
                  |                </owl:intersectionOf>
                  |            </owl:Class>
                  |        </owl:equivalentClass>
                  |        <rdfs:subClassOf rdf:resource="http://purl.obolibrary.org/obo/GO_0051052"/>
                  |        <rdfs:subClassOf>
                  |            <owl:Restriction>
                  |                <owl:onProperty rdf:resource="http://purl.obolibrary.org/obo/RO_0002211"/>
                  |                <owl:someValuesFrom rdf:resource="http://purl.obolibrary.org/obo/GO_0006310"/>
                  |            </owl:Restriction>
                  |        </rdfs:subClassOf>
                  |        <obo:IAO_0000115 rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Any process that modulates the frequency, rate or extent of DNA recombination, a DNA metabolic process in which a new        genotype is formed by reassortment of genes resulting in gene combinations different from those that were present in the parents.</obo:IAO_0000115>
                  |        <oboInOwl:id rdf:datatype="http://www.w3.org/2001/XMLSchema#string">GO:0000018</oboInOwl:id>
                  |        <oboInOwl:hasOBONamespace rdf:datatype="http://www.w3.org/2001/XMLSchema#string">biological_process</oboInOwl:hasOBONamespace>
                  |        <oboInOwl:inSubset rdf:resource="http://purl.obolibrary.org/obo/go#gosubset_prok"/>
                  |    </owl:Class>
                  |    <owl:Axiom>
                  |        <owl:annotatedTarget rdf:datatype="http://www.w3.org/2001/XMLSchema#string">Any process that modulates the frequency, rate or extent of DNA recombination, a DNA metabolic process in which a new    genotype is formed by reassortment of genes resulting in gene combinations different from those that were present in the parents.</owl:annotatedTarget>
                  |        <oboInOwl:hasDbXref rdf:datatype="http://www.w3.org/2001/XMLSchema#string">GOC:go_curators</oboInOwl:hasDbXref>
                  |        <oboInOwl:hasDbXref rdf:datatype="http://www.w3.org/2001/XMLSchema#string">ISBN:0198506732</oboInOwl:hasDbXref>
                  |        <owl:annotatedSource rdf:resource="http://purl.obolibrary.org/obo/GO_0000018"/>
                  |        <owl:annotatedProperty rdf:resource="http://purl.obolibrary.org/obo/IAO_0000115"/>
                  |    </owl:Axiom>""".stripMargin
  }
}

