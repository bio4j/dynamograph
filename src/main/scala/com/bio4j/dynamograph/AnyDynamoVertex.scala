package com.bio4j.dynamograph

import ohnosequences.scarph._
import com.bio4j.dynamograph.dao.go.AnyDynamoDbDao
import com.bio4j.dynamograph.model.GeneralSchema.id
import com.bio4j.dynamograph.model._
import scala.collection.JavaConverters._
import ohnosequences.tabula.AnyItem
import ohnosequences.tabula.AnyItem._
import ohnosequences.typesets._
import com.bio4j.dynamograph.reader.EdgeReader
import ohnosequences.tabula._, impl.ImplicitConversions._, toSDKRep._, fromSDKRep._, impl._, impl.actions._



trait AnyDynamoVertex extends AnySealedVertex { dynamoVertex =>

  type Tpe <: AnyVertexTypeWithId 
  type VertexTable <: Singleton with AnyVertexTable
  val vertexTable: VertexTable

  val dao: AnyDynamoDbDao = ServiceProvider.dao

  type Other = String

  implicit def unsafeGetOneOutEdge [
    E <: AnyDynamoEdge { type Tpe <: From[dynamoVertex.Tpe] with OneOut }
  ]
  (e: E) : GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit 
      isThere: tpe.Id ∈ tpe.record.Properties, 
      lookup: Lookup[tpe.record.Values, tpe.id.Rep],
      toItem: ToItem[SDKRep,e.edgeTables.OutItem]
    ): e.tpe.Out[E#Rep] = {
      val idV = rep get tpe.id
      val it = readOut(idV, e.edgeTables).asInstanceOf[List[E#Rep]]
      it.headOption: Option[E#Rep]
    }
  }

  implicit def unsafeGetManyOutEdge [
    E <: AnyDynamoEdge {type Tpe <: From[dynamoVertex.Tpe] with ManyOut }
  ]
  (e: E) : GetOutEdge[E] = new GetOutEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit 
      isThere: tpe.Id ∈ tpe.record.Properties, 
      lookup: Lookup[tpe.record.Values, tpe.id.Rep],
      toItem: ToItem[SDKRep,e.edgeTables.OutItem]
    ): e.tpe.Out[E#Rep] = {

      val it = readOut( rep get tpe.id, e.edgeTables).asInstanceOf[List[E#Rep]]
      it: List[E#Rep]
    }
  }

  implicit def unsafeGetOneInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with OneIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit 
      isThere: id.type ∈ dynamoVertex.tpe.record.Properties, 
      lookup: Lookup[dynamoVertex.tpe.record.Values, id.Entry],
      toItem: ToItem[SDKRep,e.edgeTables.InItem]
    ): e.tpe.In[E#Rep] = {
      val it = readIn(rep.get(id),e.edgeTables).asInstanceOf[List[E#Rep]]
      it.headOption: Option[E#Rep]
    }
  }

  implicit def unsafeGetManyInEdge[E <: Singleton with AnyDynamoEdge {
    type Tpe <: To[dynamoVertex.Tpe] with ManyIn }](e: E): GetInEdge[E] = new GetInEdge[E](e) {

    def apply(rep: dynamoVertex.Rep)(implicit 
      isThere: id.type ∈ dynamoVertex.tpe.record.Properties, 
      lookup: Lookup[dynamoVertex.tpe.record.Values, id.Entry],
      toItem: ToItem[SDKRep,e.edgeTables.InItem]
    ): e.tpe.In[E#Rep] = {
      val it = readIn(rep.get(id), e.edgeTables).asInstanceOf[List[E#Rep]]
      it: List[E#Rep]
    }
  }
  
  	def readIn[ET <: AnyEdgeTables](vId : ET#TargetId#Raw, edgeTables : ET)(implicit toItem: ToItem[SDKRep,edgeTables.InItem]) : Either[String,List[ET#EdgeRecord#Rep]] = {
    import ServiceProvider.executors._
    val queryResult = ServiceProvider.service please (QueryTable(edgeTables.inTable, Active (
      edgeTables.inTable,
      ServiceProvider.service.account,
      ThroughputStatus(1, 1)
    )) forItem edgeTables.inItem withHashKey vId)
    val result = queryResult.output match {
      case success: QuerySuccess[ET#InItem] => Right(success.item)
      case failure : QueryFailure[ET#InItem] => Left(failure.msg)
    }
    result.fold[Either[String,ET#EdgeRecord#Rep]](Left(_),_.map(single => {
      val getResult = ServiceProvider.service please (FromHashKeyTable(edgeTable, Active (
      edgeTable,
      ServiceProvider.service.account,
      ThroughputStatus(1, 1)
    )) getItem edgeItem withKey (single.get(edgeId)))
    getResult.output match {
      case success: GetItemSuccess[EdgeItem] => Right(success.item)
      case failure : GetItemFailure[EdgeItem] => Left(failure.msg)
    }
    }))
  }
  
    def readOut[ET <: Singleton with AnyEdgeTables](vId : ET#SourceId#Raw, edgeTables : ET)(implicit toItem: ToItem[SDKRep,edgeTables.OutItem]) : Either[String,List[ET#EdgeRecord#Rep]] = {
    import ServiceProvider.executors._
    val queryResult = ServiceProvider.service please (QueryTable(edgeTables.outTable, Active (
      edgeTables.outTable,
      ServiceProvider.service.account,
      ThroughputStatus(1, 1)
    )) forItem edgeTables.outItem withHashKey vId)
    val result = queryResult.output match {
      case success: QuerySuccess[ET#OutItem] => Right(success.item)
      case failure : QueryFailure[ET#OutItem] => Left(failure.msg)
    }
    result.fold[Either[String,ET#EdgeRecord#Rep]](Left(_),_.map(single => {
      val getResult = ServiceProvider.service please (FromHashKeyTable(edgeTable, Active (
      edgeTable,
      ServiceProvider.service.account,
      ThroughputStatus(1, 1)
    )) getItem edgeItem withKey (single.get(edgeId)))
    getResult.output match {
      case success: GetItemSuccess[EdgeItem] => Right(success.item)
      case failure : GetItemFailure[EdgeItem] => Left(failure.msg)
    }
    }))
  }
}

class DynamoVertex[VT <: Singleton with AnySealedVertexType, VTab <: Singleton with AnyVertexTable]
(
  val tpe: VT,
  val vertexTable: VTab
) extends AnyDynamoVertex {
  type Tpe = VT
  type VertexTable = VTab
}

object AnyDynamoVertex{
  type ofType[VT <: Singleton with AnySealedVertexType] = AnyDynamoVertex { type Tpe = VT }
}


