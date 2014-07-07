package com.bio4j.dynamograph.writer

import org.scalatest.{FlatSpec, Matchers}
import com.bio4j.dynamograph.testModel._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.model.GeneralSchema._
import scala.collection.JavaConverters._


class EdgeWriterTest extends FlatSpec with Matchers {

  "Edge writer" should "return 3 Put Item Requests" in new context {
    val writer = new EdgeWriter(testEdge,testEdgeTable)
    val result = writer.write(rep)

    result should have size 3
  }

  "Edge writer" should "return Put Item Requests for correct tables" in new context {
    val writer = new EdgeWriter(testEdge,testEdgeTable)

    val result = writer.write(rep)

    result.map(x => x.getTableName) should contain theSameElementsAs tableNames

  }

  "Edge writer" should "return Correct Put Item Requests" in new context {
    val writer = new EdgeWriter(testEdge,testEdgeTable)

    val result = writer.write(rep)

    result.find(x => x.getTableName === testEdgeTable.inTable.name).head.getItem should be (inTableItem.asJava)
    result.find(x => x.getTableName === testEdgeTable.outTable.name).head.getItem should be (outTableItem.asJava)
    result.find(x => x.getTableName === testEdgeTable.edgeTable.name).head.getItem should be (edgeTableItem.asJava)
  }

  "Edge writer" should "throw exception if relationId is not provided" in new context {
    val writer = new EdgeWriter(testEdge,testEdgeTable)
    val incorrectRep = testEdge ->> Map(sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
    intercept[NoSuchElementException]{
    val result = writer.write(incorrectRep)
    }
  }

  "Edge writer" should "throw exception if source is not provided" in new context {
    val writer = new EdgeWriter(testEdge,testEdgeTable)
    val incorrectRep = testEdge ->> Map(relationId.label -> new AttributeValue().withS("relationId"), targetId.label -> new AttributeValue().withS("targetId"))
    intercept[NoSuchElementException]{
      val result = writer.write(incorrectRep)
    }
  }

  "Edge writer" should "throw exception if target is not provided" in new context {
    val writer = new EdgeWriter(testEdge,testEdgeTable)
    val incorrectRep = testEdge ->> Map(relationId.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"))
    intercept[NoSuchElementException]{
      val result = writer.write(incorrectRep)
    }
  }

  trait context {
    val rep = testEdge ->> Map(relationId.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
    val tableNames = testEdgeTable.inTable.name :: testEdgeTable.outTable.name :: testEdgeTable.edgeTable.name :: Nil
    val inTableItem = Map(testEdgeTable.inTable.hashKey.label -> new AttributeValue().withS("targetId"), testEdgeTable.inTable.rangeKey.label -> new AttributeValue().withS("relationId"))
    val outTableItem = Map(testEdgeTable.outTable.hashKey.label -> new AttributeValue().withS("sourceId"), testEdgeTable.outTable.rangeKey.label -> new AttributeValue().withS("relationId"))
    val edgeTableItem = Map(testEdgeTable.edgeTable.hashKey.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
  }

}
