package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.testModel._
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.testModel
import scala.collection.JavaConverters._
import org.specs2.mutable._
import org.specs2.specification.Scope
import ohnosequences.tabula._, impl._, ImplicitConversions._, impl.actions._, toSDKRep._, fromSDKRep._
import ohnosequences.typesets._

class VertexWriterTest extends Specification {

  "Vertex Writer" should {
    "return single Put Item Request" in new context {
      val result = writer.write(itemRep)

      result should have size 1
    }

    "return AnyPutItemRequest for correct table" in new context {
      val result = writer.write(itemRep)

      result.head.table.name must be equalTo (testVertexTable.tableName)
    }
  }

  trait context extends Scope {
   val rep = testVertex ->> (
      testVertex.raw(
        testVertex fields (
          (testId is 1) :~:
          (testName is "testName") :~:
           ∅
          ),""
      )
    )
    val itemRep = testVertexTable.vertexItem ->> rep.fields
    case object writer extends VertexWriter(testVertexTable)
  }
}
