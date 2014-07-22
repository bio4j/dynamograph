package com.bio4j.dynamograph.model.taxonomy

import com.bio4j.dynamograph.ServiceProvider
import shapeless.Poly1
import ohnosequences.tabula._
import ohnosequences.tabula.InitialState
import ohnosequences.tabula.InitialThroughput
import ohnosequences.tabula.CreateTable

//Temporal object - until refactoring/recomendations branch will be merged
object ModelInitializer {

  val service = ServiceProvider.service

  object createTable extends Poly1{
    implicit def caseAnyTable[T <: Singleton with AnyTable, E <: Executor.For[CreateTable[T]]]
    (implicit exec: CreateTable[T] => E) =
      at[T](t => service please CreateTable(t, InitialState(t, service.account, InitialThroughput(1,1))))
  }

  object deleteTable extends Poly1{
    implicit def caseAnyTable[T <: Singleton with AnyTable, E <: Executor.For[DeleteTable[T]]]
    (implicit exec: DeleteTable[T] => E) =
      at[T](t => service please DeleteTable(t, Active(t, service.account, InitialThroughput(1,1))))
  }

  def initialize(): Unit = {
    import ServiceProvider.executors._

    TableSchema.vertexTables map createTable
    TableSchema.edgeTables map createTable
  }

  def clear(): Unit = {
    import ServiceProvider.executors._

    TableSchema.vertexTables map deleteTable
    TableSchema.edgeTables map deleteTable
  }

}
