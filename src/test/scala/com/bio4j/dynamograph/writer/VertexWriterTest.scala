package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.testModel._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.testModel
import scala.collection.JavaConverters._
import org.specs2.mutable._
import org.specs2.specification.Scope

class VertexWriterTest extends Specification {

  "Vertex Writer" should {
    "return single Put Item Request" in new context {
      val writer = new VertexWriter(testVertex,testVertexTable)
      val result = writer.write(rep)

      result should have size 1
    }

    "return Put Item Request for correct table" in new context {
      val writer = new VertexWriter(testVertex,testVertexTable)
      val result = writer.write(rep)

      result.head.getTableName must be equalTo (testVertexTable.tableName)
    }

    "return correct Put Item Request" in new context {
      val writer = new VertexWriter(testVertex,testVertexTable)
      val result = writer.write(rep)

      result.head.getItem must be equalTo (item.asJava)
    }

  }

  trait context extends Scope {
    val item = Map(testModel.id.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
    val rep = testVertex ->> Map(testModel.id.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
  }
}
