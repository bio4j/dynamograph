package com.bio4j.dynamograph

import com.bio4j.dynamograph.dynamodb.{DynamoDbExecutor, AnyDynamoDbExecutor}
import com.typesafe.scalalogging.LazyLogging
import ohnosequences.tabula._
import ohnosequences.tabula.impl.{CredentialProviderChains, DynamoDBClient, DynamoDBExecutors}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.regions.{Regions, Region}
import ohnosequences.tabula.impl.DynamoDBExecutors
import com.amazonaws.services.dynamodbv2.model.LimitExceededException
import scala.math._
import ohnosequences.tabula.Account
import ohnosequences.tabula.impl.DynamoDBExecutors
import com.bio4j.dynamograph.mapper.{GoMapper, AnyMapper}
import com.bio4j.dynamograph.writer.GoWriters

object ServiceProvider extends AnyServiceProvider {

  val ddb : AmazonDynamoDBClient = new AmazonDynamoDBClient(CredentialProviderChains.default)

  val dynamoDbExecutor : AnyDynamoDbExecutor = new DynamoDbExecutor(ddb)

  val mapper : AnyMapper = new GoMapper(GoWriters.vertexWritersMap, GoWriters.edgeWritersMap)

  val executors = new DynamoDBExecutors(
    new DynamoDBClient(EU, ddb) {
      client.setRegion(Region.getRegion(Regions.EU_WEST_1))
    }
  )

  case object service extends AnyDynamoDBService with LazyLogging  {

    type Region = EU.type
    val region = EU

    type Account = ohnosequences.tabula.Account
    val account: Account = Account("", "")

    def endpoint: String = ""

    private def withRetries[A](maxTries: Int)(f: => A) : A = {
      def retry(numberOfTries : Int) : A = {
        try{
          val startTime = System.nanoTime()
          val result = f
          val endTime = System.nanoTime()
          logger.info(s"Execution time of tabula service: ${endTime-startTime}")
          result
        }catch {
          case ex: LimitExceededException => {
            if (numberOfTries > maxTries){
              logger.debug(s"Exceeded max number of retries: $maxTries")
              throw ex
            }
            val sleepTime = pow(2,numberOfTries).toLong*50L
            logger.info(s"Sleeping for: ${sleepTime}")
            Thread.sleep(sleepTime)
            retry(numberOfTries+1)
          }
        }
      }
      retry(0)
    }

    override def please[A <: AnyAction, E <: Executor.For[A]](action: A)
    (implicit mkE: A => E): E#Out = {
      val exec = mkE(action)
      withRetries(10){
        exec()
      }
    }
  }

}
