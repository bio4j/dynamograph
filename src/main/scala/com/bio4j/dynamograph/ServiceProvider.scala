package com.bio4j.dynamograph

import com.bio4j.dynamograph.dao.go.{DynamoDbDao, AnyDynamoDbDao}
import com.bio4j.dynamograph.dynamodb.{DynamoDbExecutor, AnyDynamoDbExecutor}
import ohnosequences.tabula.{Account, EU, AnyDynamoDBService}
import ohnosequences.tabula.impl.{CredentialProviderChains, DynamoDBClient, DynamoDBExecutors}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.regions.{Regions, Region}

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
  }

}
