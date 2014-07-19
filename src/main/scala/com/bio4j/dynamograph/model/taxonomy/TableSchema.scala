package com.bio4j.dynamograph.model.taxonomy

import com.bio4j.dynamograph.model.go.TableGoSchema.{EdgeTables, VertexTable}
import com.bio4j.dynamograph.model.taxonomy.NcbiTaxonomyImplementation._
import ohnosequences.tabula.EU
import shapeless._
import javafx.scene.effect.BlendMode

object TableSchema {
  case object TaxonTable          extends VertexTable (NcbiTaxon,     "NcbiTaxon",   EU)
  case object RankTable           extends VertexTable (NcbiRank,      "NcbiRank",    EU)

  case object AssignedRankTables  extends EdgeTables  (AssignedRank, "AssignedRank", EU)
  case object SubrankTables       extends EdgeTables  (SubRank,      "Subrank",      EU)
  case object ParentTables        extends EdgeTables  (Parent,       "TaxonParent",  EU)


  val vertexTables = TaxonTable :: RankTable :: HNil

  val edgeTables = AssignedRankTables.tables ::: SubrankTables.tables ::: ParentTables.tables
}
