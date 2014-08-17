package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import com.bio4j.dynamograph.testModel.TestVertexTable
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import scala.collection.JavaConversions._

class VertexReaderTest extends Specification with Mockito {

  "Vertex Reader" should {
    "read correctly single edge" in new context {
      case object underTest extends VertexReader(TestVertexTable, ddb)
      ddb.execute(any[GetItemRequest]) returns emptyRequestResult
      val result = underTest.read("test")
      result must beEmpty
    }

    "return result of GetItemRequest invocation" in new context {
      case object underTest extends VertexReader(TestVertexTable, ddb)
      ddb.execute(any[GetItemRequest]) returns requestResult
      val result = underTest.read("test")
      result must beEqualTo(requestResult)
    }

    "correctly construct request" in new context {
      val request = new GetItemRequest()
        .withTableName(TestVertexTable.table.name)
        .withKey(Map(TestVertexTable.vertexType.id.label -> new AttributeValue().withS("tst")))
      ddb.execute(org.mockito.Matchers.eq(request)) returns requestResult
      case object underTest extends VertexReader(TestVertexTable, ddb)
      val result = underTest.read("tst")
      result must beEqualTo(requestResult)

    }

  }

  trait context extends Scope {
    val emptyRequestResult = Map[String,AttributeValue]()
    val requestResult = Map[String,AttributeValue]("test" -> new AttributeValue().withN("1L"))
    val ddb = mock[AnyDynamoDbExecutor]
  }
}
