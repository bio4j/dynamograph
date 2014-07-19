package com.bio4j.dynamograph.model.taxonomy

import com.bio4j.dynamograph.{DynamoEdge, DynamoVertex}
import com.bio4j.dynamograph.model.taxonomy.NcbiTaxonomySchema._


object NcbiTaxonomyImplementation {
  
  case object NcbiTaxon     extends DynamoVertex(NcbiTaxonType)
  case object NcbiRank      extends DynamoVertex(NcbiRankType)
  
  case object Parent        extends DynamoEdge(NcbiTaxon, ParentType, NcbiTaxon)
  case object AssignedRank  extends DynamoEdge(NcbiTaxon, AssignedRankType, NcbiRank)
  case object SubRank       extends DynamoEdge(NcbiRank, SubrankType, NcbiRank)

}
