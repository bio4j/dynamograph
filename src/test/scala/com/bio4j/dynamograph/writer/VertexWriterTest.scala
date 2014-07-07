package com.bio4j.dynamograph.writer

import org.scalatest._
import com.bio4j.dynamograph.testModel._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.testModel
import scala.collection.JavaConverters._
import com.bio4j.dynamograph.model.GeneralSchema.{targetId, sourceId, relationId}

class VertexWriterTest extends FlatSpec with Matchers {

  "Vertex Writer" should "return sinle Put Item Request" in new context {
    val writer = new VertexWriter(testVertex,testVertexTable)
    val result = writer.write(rep)

    result should have size 1
  }

  "Vertex Writer" should "return Put Item Request for correct table" in new context {
    val writer = new VertexWriter(testVertex,testVertexTable)
    val result = writer.write(rep)

    result.head.getTableName should equal (testVertexTable.tableName)
  }

  "Vertex Writer" should "return correct Put Item Request" in new context {
    val writer = new VertexWriter(testVertex,testVertexTable)
    val result = writer.write(rep)

    result.head.getItem should be (item.asJava)
  }

  trait context {
    val item = Map(testModel.id.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
    val rep = testVertex ->> Map(testModel.id.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
  }
}
