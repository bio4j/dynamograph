package com.bio4j.dynamograph.model.go

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import ohnosequences.tabula.{AnyHash, AnyRegion, Hash, EU}
import com.bio4j.dynamograph.model.go.GOImplementation._
import com.bio4j.dynamograph.model.go.GOSchema.id


object TableGOImplementation {

  case object GOTermTable extends VertexTable[GOTerm.type, AnyHash, AnyRegion]("GOTerm",Hash(id),EU)

  case object IsATables extends EdgeTables[IsA.type, AnyRegion]("GOIsA",EU)

  case object HasPartTables extends EdgeTables[HasPart.type, AnyRegion]("GOHasPart",EU)

  case object PartOfTables extends EdgeTables[PartOf.type, AnyRegion]("GOPartOf",EU)

  case object NegativelyRegulatesTables extends EdgeTables[NegativelyRegulates.type, AnyRegion]("GONegativelyRegulates",EU)

  case object PositivelyRegulatesTables extends EdgeTables[PositivelyRegulates.type, AnyRegion]("GOPositivelyRegulates",EU)
}
