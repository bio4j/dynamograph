package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.testModel
import com.bio4j.dynamograph.testModel._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.collection.JavaConverters._
import org.specs2.mutable._
import org.specs2.specification.Scope

class VertexWriterTest extends Specification {

  "Vertex Writer" should {
    "return single Put Item Request" in new context {
      object writer extends VertexWriter(TestVertexTable)
      val result = writer.write(rep)

      result should have size 1
    }

    "return Put Item Request for correct table" in new context {
      object writer extends VertexWriter(TestVertexTable)
      val result = writer.write(rep)

      result.head.getTableName must be equalTo (TestVertexTable.tableName)
    }

    "return correct Put Item Request" in new context {
      object writer extends VertexWriter(TestVertexTable)
      val result = writer.write(rep)

      result.head.getItem must be equalTo (item.asJava)
    }

  }

  trait context extends Scope {
    val item = Map(testModel.testId.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
    val rep = TestVertex ->> Map(testModel.testId.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
  }
}
