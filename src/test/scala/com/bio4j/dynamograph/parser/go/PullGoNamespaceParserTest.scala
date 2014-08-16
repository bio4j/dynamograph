package com.bio4j.dynamograph.parser.go

import com.bio4j.dynamograph.model.Properties.name
import com.bio4j.dynamograph.model.go.GoSchema.GoNamespacesType
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

import scala.io.Source

class PullGoNamespaceParserTest extends Specification {

  "PullGoNamespaceParser" should {

    "return singleElements" in new context {
      val underTest = new PullGoNamespaceParser(Source.fromString(singleEntry))
      underTest.foreach(result += _)
      result must have size 1
    }

    "return 3 singleElements" in new context {
      val underTest = new PullGoNamespaceParser(Source.fromString(file))
      underTest.foreach(result += _)
      result must have size 3
    }

    "parse correctly single Entry" in new context {
      val underTest = new PullGoNamespaceParser(Source.fromString(singleEntry))
      underTest.foreach(result += _)
      val element = result.head
      element must beEqualTo(expectedVertexValue)
    }
  }

  trait context extends Scope {

    val result = scala.collection.mutable.MutableList[SingleElement]()

    val expectedVertexValue = new SingleElement(Map(
      name.label -> "biological_process",
      ParsingContants.vertexType -> GoNamespacesType.label
    ),Nil)

    val singleEntry = """biological_process""".stripMargin

    val file =  """biological_process
                  |molecular_function
                  |cellular_component""".stripMargin
  }
}
