package com.bio4j.dynamograph.mapper

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, PutItemRequest}
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.go.GoSchema.IsAType
import com.bio4j.dynamograph.parser.{ParsingContants, SingleElement}
import com.bio4j.dynamograph.testModel
import com.bio4j.dynamograph.testModel.{TestVertex, _}
import com.bio4j.dynamograph.writer.{AnyEdgeWriter, AnyVertexWriter}
import org.specs2.mock._
import org.specs2.mutable._
import org.specs2.specification.Scope

class GoMapperTest extends Specification with Mockito {

  "GoMapper " should {

    "throw exception for empty single element" in new context {
      underTest.map(SingleElement(Map(),Nil)) must throwA[NoSuchElementException]
    }

    "throw exception for unknown vertex type " in new context {
      override val underTest = new GoMapper(Map(), Map())
      underTest.map(
        SingleElement(Map(id.label -> "testLabel", ParsingContants.vertexType -> TestVertexType.label),Nil)
      ) must throwA[NoSuchElementException]
    }


    "throw exception for unknown vertex type " in new context {
      override val underTest = new GoMapper(vertexWriters, Map())
      underTest.map(
        SingleElement(Map(id.label -> "testLabel", ParsingContants.vertexType -> "aa"),Nil)
      ) must throwA[NoSuchElementException]
    }

    "throw exception for vertex attributes without id" in new context{
      override val underTest = new GoMapper(vertexWriters, Map())
      underTest.map(
        SingleElement(Map(testModel.name.label -> "testLabel", ParsingContants.vertexType -> TestVertexType.label),Nil)
      ) must throwA[NoSuchElementException]
    }

    "return single PutItemRequest" in new context {
      override val underTest = new GoMapper(vertexWriters, Map())
      vertexWriter.write(any[Map[String,AttributeValue]]) returns List(new PutItemRequest())
      val result = underTest.map(
        SingleElement(Map(id.label -> "testLabel", ParsingContants.vertexType -> TestVertexType.label
        ),Nil))
      result should have size 1
    }

    "return PutItemRequests for vertex and edge" in new context {
      vertexWriter.write(any[Map[String,AttributeValue]]) returns List(new PutItemRequest())
      edgeWriter.write(any[Map[String,AttributeValue]]) returns List(new PutItemRequest(), new PutItemRequest(), new PutItemRequest())
      val result = underTest.map(
        SingleElement(Map(id.label -> "testLabel", ParsingContants.vertexType -> TestVertexType.label),
        List(Map(ParsingContants.relationType -> TestEdgeType.label, targetId.label -> "GO:0048308"))))
      result should have size 4
    }

    "return PutItemRequests for vertex and edges" in new context {
      vertexWriter.write(any[Map[String,AttributeValue]]) returns List(new PutItemRequest())
      edgeWriter.write(any[Map[String,AttributeValue]]) returns List(new PutItemRequest(), new PutItemRequest(), new PutItemRequest())
      val result = underTest.map(SingleElement(Map(id.label -> "testLabel", ParsingContants.vertexType -> TestVertexType.label
      ),firstEdgesValues))
      result should have size 10
    }

    "throw exception for unknown relation type" in new context {
      vertexWriter.write(any[Map[String,AttributeValue]]) returns List(new PutItemRequest())
      underTest.map(SingleElement(Map(id.label -> "testLabel", ParsingContants.vertexType -> TestVertexType.label),
        List(Map(ParsingContants.relationType -> IsAType.label, targetId.label -> "GO:0048308")))) must throwA[NoSuchElementException]
    }

  }

  trait context extends Scope {
    val firstEdgesValues = List(
      Map(ParsingContants.relationType -> TestEdgeType.label, targetId.label -> "GO:0048308"),
      Map(ParsingContants.relationType -> TestEdgeType.label, targetId.label -> "GO:0048311"),
      Map(ParsingContants.relationType -> TestEdgeType.label, targetId.label -> "biological_process")
    )

    val vertexWriter = mock[AnyVertexWriter]
    val vertexWriters = Map(TestVertexType.label -> vertexWriter)
    val edgeWriter = mock[AnyEdgeWriter]
    val edgeWriters = Map(TestEdgeType.label -> edgeWriter)
    val underTest = new GoMapper(vertexWriters, edgeWriters)
  }

}