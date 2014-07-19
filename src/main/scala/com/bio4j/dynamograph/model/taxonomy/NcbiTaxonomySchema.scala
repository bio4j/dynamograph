package com.bio4j.dynamograph.model.taxonomy

import ohnosequences.scarph.{ManyToOne, VertexType}
import com.bio4j.dynamograph.model.Properties._


object NcbiTaxonomySchema {

  case object NcbiTaxonType     extends VertexType("NcbiTaxon")
  implicit val NcbiTaxonType_id                = NcbiTaxonType has id
  implicit val NcbiTaxonType_name              = NcbiTaxonType has name
  implicit val NcbiTaxonType_comment           = NcbiTaxonType has comment
  implicit val NcbiTaxonType_scientificName    = NcbiTaxonType has scientificName

  case object NcbiRankType      extends VertexType("Rank")
  implicit val RankType_name                   = NcbiRankType has name
  implicit val RankType_number                 = NcbiRankType has number

  // edges
  case object ParentType         extends ManyToOne(NcbiTaxonType, "Parent",       NcbiTaxonType)
  case object AssignedRankType   extends ManyToOne(NcbiTaxonType, "AssignedRank", NcbiRankType)
  case object SubrankType        extends ManyToOne(NcbiRankType,  "Subrank",      NcbiRankType)

 }
