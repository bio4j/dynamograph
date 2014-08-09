package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.testModel._
import ohnosequences.typesets._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.model.GeneralSchema._
import scala.collection.JavaConverters._
import org.specs2.mutable._
import org.specs2.specification.Scope
import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._


class EdgeWriterTest extends Specification {

  "Edge writer" should {
    "return 3 Put Item Requests" in new context {
      val result = writer.write(repFields)

      result must have size 3
    }

    "return AnyPutItemRequests for correct tables" in new context {

      val result = writer.write(repFields)

      result.map(x => x.table.name) must containTheSameElementsAs(tableNames)
    }
  }

  trait context extends Scope {
    val rep = testEdge ->> (
      testEdge.raw(
        testEdge fields (
          (relationId is "relationId") :~:
          (sourceId is "sourceId") :~:
          (targetId is "targetId") :~:
           ∅
          ),""
      )
    )
    val repFields = rep.fields
    case object writer extends EdgeWriter(testEdgeTable)
    val tableNames = testEdgeTable.inTable.name :: testEdgeTable.outTable.name :: testEdgeTable.edgeTable.name :: Nil
    val inTableItem = Map(testEdgeTable.inTable.hashKey.label -> new AttributeValue().withS("targetId"), testEdgeTable.inTable.rangeKey.label -> new AttributeValue().withS("relationId"))
    val outTableItem = Map(testEdgeTable.outTable.hashKey.label -> new AttributeValue().withS("sourceId"), testEdgeTable.outTable.rangeKey.label -> new AttributeValue().withS("relationId"))
    val edgeTableItem = Map(testEdgeTable.edgeTable.hashKey.label -> new AttributeValue().withS("relationId"), sourceId.label -> new AttributeValue().withS("sourceId"), targetId.label -> new AttributeValue().withS("targetId"))
  }

}
