package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.bio4j.dynamograph.model._

import ohnosequences.tabula._

import com.bio4j.dynamograph.{ServiceProvider, AnyDynamoVertex}
import com.bio4j.dynamograph.model.GeneralSchema.id
import ohnosequences.tabula.impl._, actions._, ImplicitConversions._
import scala.collection.JavaConversions._
import ohnosequences.typesets._
import ohnosequences.scarph._

trait AnyVertexReader { vertexReader =>

  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable: VertexTable

  type Table = vertexTable.Table
  val table: Table = vertexTable.table
  type Item = vertexTable.VertexItem
  val item: Item = vertexTable.vertexItem

  type Record = vertexTable.record.type
  val record : Record = vertexTable.record

  import ServiceProvider.executors._
  def read(identifier : vertexTable.VertexId#Value) : Either[String,record.Rep] = {
    val getResult = ServiceProvider.service please (FromHashKeyTable(table, Active (
      table,
      ServiceProvider.service.account,
      ThroughputStatus(1, 1)
    )) getItem item withKey (identifier))
    getResult.output match {
      case success: GetItemSuccess[Item] => Right(record fields success.item)
      case failure : GetItemFailure[Item] => Left(failure.msg)
    }
  }

}

class VertexReader[VT <: AnyVertexTable](val vertexTable: VT) extends AnyVertexReader {
  type VertexTable = VT

}

object AnyVertexReader{
  type withVertexType[VT <: Singleton with AnySealedVertexType] = AnyVertexReader { type VertexTable = AnyVertexTable.withVertexType[VT] }
}
