package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.ServiceProvider
import ohnosequences.tabula.InitialState
import ohnosequences.tabula.InitialThroughput
import ohnosequences.tabula.CreateTable
import ohnosequences.tabula.DeleteTable
import com.bio4j.dynamograph.model.go.TableGoSchema.VertexTable


object TableModelInitializer {

  val service = ServiceProvider.service

  def initialize() = {
    import ServiceProvider.executors._
    for (table <- TableGoImplementation.vertexTables){
      val casted = table.asInstanceOf[Singleton with VertexTable[_,_]]
      service please CreateTable(casted, InitialState(casted, service.account, InitialThroughput(1,1)))
    }

    for (edgeTablesAggregate <- TableGoImplementation.edgeTables){
      service please CreateTable(edgeTablesAggregate.inTable, InitialState(edgeTablesAggregate.inTable, service.account, InitialThroughput(1,1)))
      service please CreateTable(edgeTablesAggregate.outTable, InitialState(edgeTablesAggregate.outTable, service.account, InitialThroughput(1,1)))
      service please CreateTable(edgeTablesAggregate.edgeTable, InitialState(edgeTablesAggregate.edgeTable, service.account, InitialThroughput(1,1)))
    }
  }

  def clear() = {
    import ServiceProvider.executors._
    for (table <- TableGoImplementation.vertexTables){
      val casted = table.asInstanceOf[Singleton with VertexTable[_,_]]
      service please DeleteTable(casted, Active(casted, service.account, ThroughputStatus(1,1)))
    }

    for (edgeTablesAggregate <- TableGoImplementation.edgeTables){
      service please DeleteTable(edgeTablesAggregate.inTable, Active(edgeTablesAggregate.inTable, service.account, ThroughputStatus(1,1)))
      service please DeleteTable(edgeTablesAggregate.outTable, Active(edgeTablesAggregate.outTable, service.account, ThroughputStatus(1,1)))
      service please DeleteTable(edgeTablesAggregate.edgeTable, Active(edgeTablesAggregate.edgeTable, service.account, ThroughputStatus(1,1)))
    }
  }
}
