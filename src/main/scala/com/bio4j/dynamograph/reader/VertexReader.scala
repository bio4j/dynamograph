package com.bio4j.dynamograph.reader

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, GetItemRequest}
import com.bio4j.dynamograph.model._
import com.bio4j.dynamograph._
import com.bio4j.dynamograph.model.GeneralSchema.id
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._, impl._, impl.actions._
import scala.collection.JavaConversions._
import ohnosequences.typesets._, AnyTag._
import ohnosequences.scarph._

trait AnyVertexReader { vertexReader =>

  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable: VertexTable
  
  type Table = vertexTable.Table
  val table: Table = vertexTable.table
  type Item = vertexTable.VertexItem
  val item: Item = vertexTable.vertexItem
  
  type Record = vertexTable.Record
  val record : Record = vertexTable.record
  
  type VertexId = vertexTable.VertexId
  val vertexId = vertexTable.vertexId
  
  implicit val containId : VertexId ∈ Record#Properties

  
  def read(identifier : vertexId.Value)(implicit from : ToItem[SDKRep, Item]) : Either[String,Item#Record#Rep] = {
    import ServiceProvider.executors._
    val getResult = ServiceProvider.service please (FromHashKeyTable(table, Active (
      table,
      ServiceProvider.service.account,
      ThroughputStatus(1, 1)
    )) getItem item withKey (identifier))
    getResult.output match {
      case success: GetItemSuccess[Item] => Right(success.item)
      case failure : GetItemFailure[Item] => Left(failure.msg)
    }
  }
//((edgeRep as inItem.record):inItem.record.Raw)
}

class VertexReader[VT <: AnyVertexTable](val vertexTable: VT) extends AnyVertexReader {
  type VertexTable = VT

}

object AnyVertexReader{
  type withVertexType[VT <: Singleton with AnyVertexTypeWithId] = AnyVertexReader { type VertexTable = AnyVertexTable.withVertexType[VT] }
}
