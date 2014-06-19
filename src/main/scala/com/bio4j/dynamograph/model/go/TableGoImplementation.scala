package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import ohnosequences.tabula.{AnyRegion, EU}
import com.bio4j.dynamograph.model.go.GoImplementation._


object TableGoImplementation {

  case object GoTermTable extends VertexTable[GoTerm.type, AnyRegion]("GoTerm", EU)

  case object GoNamespacesTable extends VertexTable[GoNamespaces.type, AnyRegion]("GoNamespaces", EU)

  case object IsATables extends EdgeTables[IsA.type, AnyRegion]("GoIsA", EU)

  case object HasPartTables extends EdgeTables[HasPart.type, AnyRegion]("GoHasPart",EU)

  case object PartOfTables extends EdgeTables[PartOf.type, AnyRegion]("GoPartOf", EU)

  case object NegativelyRegulatesTables extends EdgeTables[NegativelyRegulates.type, AnyRegion]("GoNegativelyRegulates", EU)

  case object PositivelyRegulatesTables extends EdgeTables[PositivelyRegulates.type, AnyRegion]("GoPositivelyRegulates", EU)

  case object RegulatesTables extends EdgeTables[Regulates.type, AnyRegion]("GoRegulates", EU)
}
