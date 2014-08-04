package com.bio4j.dynamograph.model.go

import ohnosequences.tabula._
import com.bio4j.dynamograph.ServiceProvider
import shapeless._
import com.bio4j.dynamograph.model.go.TableGoImplementation.{GoTermTable, GoNamespacesTable}
import com.bio4j.dynamograph.model.go.TableGoSchema.EdgeTables


object TableModelInitializer {

  // TODO class with method for this etc
  val service = ServiceProvider.service

  object createTable extends Poly1 {
    implicit def caseAnyTable[T <: Singleton with AnyTable, E <: Executor.For[CreateTable[T]]]
    (implicit exec: CreateTable[T] => E) =
      at[T](t => service please CreateTable(t, InitialState(t, service.account, InitialThroughput(1,1))))
  }

  object deleteTable extends Poly1 {
    implicit def caseAnyTable[T <: Singleton with AnyTable, E <: Executor.For[DeleteTable[T]]]
    (implicit exec: DeleteTable[T] => E) =
      at[T](t => service please DeleteTable(t, Active(t, service.account, InitialThroughput(1,1))))
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
