package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoImplementation.GoTermTable
import ohnosequences.tabula.InitialState
import ohnosequences.tabula.InitialThroughput
import ohnosequences.tabula.CreateTable
import com.bio4j.dynamograph.ServiceProvider


object TableModelInitializer {

  val service = ServiceProvider.service



  def initialize() = {
    import ServiceProvider.executors._
    for (tables <- TableGoImplementation.vertexTables){
      service please CreateTable(GoTermTable, InitialState(GoTermTable, service.account, InitialThroughput(1,1)))
    }

    for (edgeTablesAggregate <- TableGoImplementation.edgeTables){
      service please CreateTable(edgeTablesAggregate.inTable, InitialState(edgeTablesAggregate.inTable, service.account, InitialThroughput(1,1)))
      service please CreateTable(edgeTablesAggregate.outTable, InitialState(edgeTablesAggregate.outTable, service.account, InitialThroughput(1,1)))
      service please CreateTable(edgeTablesAggregate.edgeTable, InitialState(edgeTablesAggregate.edgeTable, service.account, InitialThroughput(1,1)))
    }
  }
}
