package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.ServiceProvider
import shapeless._
import com.bio4j.dynamograph.model.go.TableGoImplementation.{GoTermTable, GoNamespacesTable}
import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables


object TableModelInitializer {

  val service = ServiceProvider.service

  object createTable extends Poly1{
    import ServiceProvider.executors._
    implicit def caseAnyHashKeyTable[T<: scala.Singleton with AnyHashKeyTable] = at[T](x =>
      service please CreateTable(x, InitialState(x, service.account, InitialThroughput(1,1)))
    )
    implicit def caseAnyCompositeTable[T<: EdgeTables[_,_]] = at[T](x => {
        service please CreateTable(x.inTable, InitialState(x.inTable, service.account, InitialThroughput(1,1)))
        service please CreateTable(x.outTable, InitialState(x.outTable, service.account, InitialThroughput(1,1)))
        service please CreateTable(x.edgeTable, InitialState(x.edgeTable, service.account, InitialThroughput(1,1)))
      }
    )
  }

  object deleteTable extends Poly1{
    import ServiceProvider.executors._
    implicit def caseAnyHashKeyTable[T<: scala.Singleton with AnyHashKeyTable] = at[T](x =>
      service please DeleteTable(x, Active(x, service.account, InitialThroughput(1,1)))
    )
    implicit def caseAnyCompositeTable[T<: EdgeTables[_,_]] = at[T](x => {
        service please DeleteTable(x.inTable, Active(x.inTable, service.account, ThroughputStatus(1,1)))
        service please DeleteTable(x.outTable, Active(x.outTable, service.account, ThroughputStatus(1,1)))
        service please DeleteTable(x.edgeTable, Active(x.edgeTable, service.account, ThroughputStatus(1,1)))
      }
    )
  }

  def initialize(): Unit = {
    import ServiceProvider.executors._

    TableGoImplementation.vertexTables map createTable
    TableGoImplementation.edgeTables map createTable
  }

  def clear(): Unit = {
    import ServiceProvider.executors._

    TableGoImplementation.vertexTables map deleteTable
    TableGoImplementation.edgeTables map deleteTable
  }
}
