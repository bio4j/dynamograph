package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import ohnosequences.tabula.{AnyRegion, EU}
import com.bio4j.dynamograph.model.go.GOImplementation._


object TableGOImplementation {

  case object GOTermTable extends VertexTable[GOTerm.type, AnyRegion]("GOTerm", EU)

  case object GONamespacesTable extends VertexTable[GONamespaces.type, AnyRegion]("G0Namespaces", EU)

  case object IsATables extends EdgeTables[IsA.type, AnyRegion]("GOIsA", EU)

  case object HasPartTables extends EdgeTables[HasPart.type, AnyRegion]("GOHasPart",EU)

  case object PartOfTables extends EdgeTables[PartOf.type, AnyRegion]("GOPartOf", EU)

  case object NegativelyRegulatesTables extends EdgeTables[NegativelyRegulates.type, AnyRegion]("GONegativelyRegulates", EU)

  case object PositivelyRegulatesTables extends EdgeTables[PositivelyRegulates.type, AnyRegion]("GOPositivelyRegulates", EU)
}
