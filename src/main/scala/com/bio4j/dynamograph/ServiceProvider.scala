package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.{DynamoDbDao, AnyDynamoDbDao}
import com.bio4j.dynamograph.dynamodb.{DynamoDbExecutor, AnyDynamoDbExecutor}
import ohnosequences.tabula._
import ohnosequences.tabula.impl.{CredentialProviderChains, DynamoDBClient, DynamoDBExecutors}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.regions.{Regions, Region}
import ohnosequences.tabula.impl.DynamoDBExecutors
import ohnosequences.tabula.Account
import com.amazonaws.services.dynamodbv2.model.LimitExceededException
import scala.math._
import ohnosequences.tabula.Account
import ohnosequences.tabula.impl.DynamoDBExecutors

object ServiceProvider extends AnyServiceProvider {

  val dao = new DynamoDbDao

  val ddb : AmazonDynamoDBClient = new AmazonDynamoDBClient(CredentialProviderChains.default)

  val dynamoDbExecutor : AnyDynamoDbExecutor = new DynamoDbExecutor(ddb)

  val executors = new DynamoDBExecutors(
    new DynamoDBClient(EU, ddb) {
      client.setRegion(Region.getRegion(Regions.EU_WEST_1))
    }
  )

  case object service extends AnyDynamoDBService {
    type Region = EU.type
    val region = EU

    type Account = ohnosequences.tabula.Account
    val account: Account = Account("", "")

    def endpoint: String = "" //shouldn't be here

    private def withRetries[A](maxTries: Int)(f: => A) : A = {
      def retry(numberOfTries : Int) : A = {
        try{
          f
        }catch {
          case ex: LimitExceededException => {
            if (numberOfTries > maxTries)
              throw ex
            Thread.sleep(pow(2,numberOfTries).toLong*50L)
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
