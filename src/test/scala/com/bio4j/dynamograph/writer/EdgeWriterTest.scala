package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.testModel._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.model.GeneralSchema._
import scala.collection.JavaConverters._
import org.specs2.mutable._
import org.specs2.specification.Scope


class EdgeWriterTest extends Specification {

  "Edge writer" should {
    "return 3 Put Item Requests" in new context {
      val writer = new EdgeWriter(testEdge,testEdgeTable)
      val result = writer.write(rep)

      result must have size 3
    }

    "return Put Item Requests for correct tables" in new context {
      val writer = new EdgeWriter(testEdge,testEdgeTable)

      val result = writer.write(rep)

      result.map(x => x.getTableName) must containTheSameElementsAs(tableNames)

    }

    "return Correct Put Item Requests" in new context {
      val writer = new EdgeWriter(testEdge,testEdgeTable)

      val result = writer.write(rep)

      result.find(x => x.getTableName == testEdgeTable.inTable.name).head.getItem must be equalTo (inTableItem.asJava)
      result.find(x => x.getTableName == testEdgeTable.outTable.name).head.getItem must be equalTo (outTableItem.asJava)
      result.find(x => x.getTableName == testEdgeTable.edgeTable.name).head.getItem must be equalTo (edgeTableItem.asJava)
    }

    "throw exception if relationId is not provided" in new context {
      val writer = new EdgeWriter(testEdge,testEdgeTable)
      val incorrectRep = testEdge ->> Map(sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
      writer.write(incorrectRep) must throwA[NoSuchElementException]
    }

    "throw exception if source is not provided" in new context {
      val writer = new EdgeWriter(testEdge,testEdgeTable)
      val incorrectRep = testEdge ->> Map(relationId.label -> new AttributeValue().withS("relationId"), targetId.label -> new AttributeValue().withS("targetId"))
      writer.write(incorrectRep) must throwA[ NoSuchElementException]
    }

    "throw exception if target is not provided" in new context {
      val writer = new EdgeWriter(testEdge,testEdgeTable)
      val incorrectRep = testEdge ->> Map(relationId.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"))
      writer.write(incorrectRep) must throwA[NoSuchElementException]
    }

  }

  trait context extends Scope {
    val rep = testEdge ->> Map(relationId.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
    val tableNames = testEdgeTable.inTable.name :: testEdgeTable.outTable.name :: testEdgeTable.edgeTable.name :: Nil
    val inTableItem = Map(testEdgeTable.inTable.hashKey.label -> new AttributeValue().withS("targetId"), testEdgeTable.inTable.rangeKey.label -> new AttributeValue().withS("relationId"))
    val outTableItem = Map(testEdgeTable.outTable.hashKey.label -> new AttributeValue().withS("sourceId"), testEdgeTable.outTable.rangeKey.label -> new AttributeValue().withS("relationId"))
    val edgeTableItem = Map(testEdgeTable.edgeTable.hashKey.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
  }

}
