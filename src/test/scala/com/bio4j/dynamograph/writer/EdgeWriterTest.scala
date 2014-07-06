package com.bio4j.dynamograph.writer

import org.scalatest.{Matchers, FunSuite}
import com.bio4j.dynamograph.testModel.{testVertex, testVertexTable, testVertexType}
import com.bio4j.dynamograph.testModel
import com.amazonaws.services.dynamodbv2.model.AttributeValue


class EdgeWriterTest extends FunSuite with Matchers {

  "Write" should "return correct List of put item operations" in {
    val writer = new EdgexWriter(testEdgeType,testEdgeTable)
    val item = Map(testModel.id.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
    val rep = testVertex ->> Map(testModel.id.label -> new AttributeValue().withS("testId"), testModel.name.label -> new AttributeValue().withS("testName"))
    val result = writer.write(rep)

    result should have size 1
    result.head.getTableName should equal (testVertexTable.tableName)
    result.head.getItem should be (item)
  }

}
