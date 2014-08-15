package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model._
import com.bio4j.dynamograph.dynamodb.AnyDynamoDbExecutor
import com.bio4j.dynamograph.model.Properties.targetId
import com.bio4j.dynamograph.parser.ParsingContants
import com.bio4j.dynamograph.testModel.{TestEdgeTables, TestVertexType}
import org.specs2.mutable.Specification
import org.specs2.mock._
import org.specs2.specification.Scope
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

class EdgeReaderTest extends Specification with Mockito {

  "Edge reader " should {
    "read correctly in edges " in new context {
      ddb.execute(any[QueryRequest]) returns inQueryResult
      ddb.execute(any[BatchGetItemRequest]) returns inBatch
      case object underTest extends EdgeReader(TestEdgeTables,ddb)
      val result = underTest.readIn("testId")
      result should have size 3
    }

    "read correctly out edges " in new context {
      ddb.execute(any[QueryRequest]) returns outQueryResult
      ddb.execute(any[BatchGetItemRequest]) returns outBatch
      case object underTest extends EdgeReader(TestEdgeTables,ddb)
      val result = underTest.readIn("testId")
      result should have size 2
    }

    "invoke batchItemRequest with proper values for readOut" in new context {
      val batchRequest = new BatchGetItemRequest().withRequestItems(
        Map(TestEdgeTables.edgeTable.name -> new KeysAndAttributes().withKeys(
          List(Map(TestEdgeTables.edgeTable.hashKey.label -> new AttributeValue().withS("GO:2048311")).asJava).asJava)))
      ddb.execute(any[QueryRequest]) returns outQueryResult
      ddb.execute(org.mockito.Matchers.eq(batchRequest)) returns outBatch
      case object underTest extends EdgeReader(TestEdgeTables,ddb)
      val result = underTest.readOut("testId")
      result should have size 2
    }

    "invoke batchItemRequest with proper values for readIn" in new context {
      val batchRequest = new BatchGetItemRequest().withRequestItems(
        Map(TestEdgeTables.edgeTable.name -> new KeysAndAttributes().withKeys(
          List(Map(TestEdgeTables.edgeTable.hashKey.label -> new AttributeValue().withS("GO:0048311")).asJava).asJava)))
      ddb.execute(any[QueryRequest]) returns inQueryResult
      ddb.execute(org.mockito.Matchers.eq(batchRequest)) returns inBatch
      case object underTest extends EdgeReader(TestEdgeTables,ddb)
      val result = underTest.readIn("testId")
      result should have size 3
    }

    "invoke QueryRequest with proper values for readOut" in new context {
      val hashKeyCondition = new Condition()
        .withComparisonOperator(ComparisonOperator.EQ)
        .withAttributeValueList(new AttributeValue().withS("testId"));
      val request = new QueryRequest().withTableName(TestEdgeTables.outTable.name).withKeyConditions(
        Map(TestEdgeTables.outTable.hashKey.label -> hashKeyCondition).asJava)
      ddb.execute(org.mockito.Matchers.eq(request)) returns outQueryResult
      ddb.execute(any[BatchGetItemRequest]) returns outBatch
      case object underTest extends EdgeReader(TestEdgeTables,ddb)
      val result = underTest.readOut("testId")
      result should have size 2
    }

    "invoke QueryRequest with proper values for readIn" in new context {
      val hashKeyCondition = new Condition()
        .withComparisonOperator(ComparisonOperator.EQ)
        .withAttributeValueList(new AttributeValue().withS("testId"));
      val request = new QueryRequest().withTableName(TestEdgeTables.inTable.name).withKeyConditions(
        Map(TestEdgeTables.inTable.hashKey.label -> hashKeyCondition).asJava)
      ddb.execute(org.mockito.Matchers.eq(request)) returns inQueryResult
      ddb.execute(any[BatchGetItemRequest]) returns inBatch
      case object underTest extends EdgeReader(TestEdgeTables,ddb)
      val result = underTest.readIn("testId")
      result should have size 3
    }



  }

  trait context extends Scope {
    val firstEdgesValues = List(
      Map(ParsingContants.relationType -> TestVertexType.label, targetId.label -> "GO:0048308"),
      Map(ParsingContants.relationType -> TestVertexType.label, targetId.label -> "GO:0048311"),
      Map(ParsingContants.relationType -> TestVertexType.label, targetId.label -> "biological_process")
    )
    val inQueryResult = List(Map(TestEdgeTables.inTable.hashKey.label -> new AttributeValue().withS("GO:0048311")) )
    val inBatch = List(Map[String,AttributeValue](),Map[String,AttributeValue](),Map[String,AttributeValue]())
    //List[Map[String, AttributeValue]]
    val outQueryResult = List(Map(TestEdgeTables.outTable.hashKey.label -> new AttributeValue().withS("GO:2048311")))
    val outBatch = List(Map[String,AttributeValue](),Map[String,AttributeValue]())
    val ddb = mock[AnyDynamoDbExecutor]
  }

}
