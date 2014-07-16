package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.ServiceProvider
import shapeless._
import com.bio4j.dynamograph.model.go.TableGoImplementation.{GoTermTable, GoNamespacesTable}


object TableModelInitializer {

  val service = ServiceProvider.service

  object createTable extends Poly1{
    import ServiceProvider.executors._
    implicit def caseGoTermTable = at[GoTermTable.type](x =>
      service please CreateTable(x, InitialState(x, service.account, InitialThroughput(1,1))))
    implicit def caseGoNamespaceTable = at[GoNamespacesTable.type](x =>
      service please CreateTable(x, InitialState(x, service.account, InitialThroughput(1,1))))
  }

  object deleteTable extends Poly1{
    import ServiceProvider.executors._
    implicit def caseGoTermTable = at[GoTermTable.type](x =>
      service please DeleteTable(x, Active(x, service.account, InitialThroughput(1,1))))
    implicit def caseGoNamespaceTable = at[GoNamespacesTable.type](x =>
      service please DeleteTable(x, Active(x, service.account, InitialThroughput(1,1))))
  }

  def initialize(): Unit = {
    import ServiceProvider.executors._

    TableGoImplementation.vertexTables map createTable

    for (edgeTablesAggregate <- TableGoImplementation.edgeTables){
      service please CreateTable(edgeTablesAggregate.inTable, InitialState(edgeTablesAggregate.inTable, service.account, InitialThroughput(1,1)))
      service please CreateTable(edgeTablesAggregate.outTable, InitialState(edgeTablesAggregate.outTable, service.account, InitialThroughput(1,1)))
      service please CreateTable(edgeTablesAggregate.edgeTable, InitialState(edgeTablesAggregate.edgeTable, service.account, InitialThroughput(1,1)))
    }
  }

  def clear(): Unit = {
    import ServiceProvider.executors._

    TableGoImplementation.vertexTables map deleteTable

    for (edgeTablesAggregate <- TableGoImplementation.edgeTables){
      service please DeleteTable(edgeTablesAggregate.inTable, Active(edgeTablesAggregate.inTable, service.account, ThroughputStatus(1,1)))
      service please DeleteTable(edgeTablesAggregate.outTable, Active(edgeTablesAggregate.outTable, service.account, ThroughputStatus(1,1)))
      service please DeleteTable(edgeTablesAggregate.edgeTable, Active(edgeTablesAggregate.edgeTable, service.account, ThroughputStatus(1,1)))
    }
  }
}
