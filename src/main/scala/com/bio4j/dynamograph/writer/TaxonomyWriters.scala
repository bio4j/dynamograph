package com.bio4j.dynamograph.writer

import com.bio4j.dynamograph.model.taxonomy.NcbiTaxonomyImplementation._
import com.bio4j.dynamograph.model.taxonomy.TableSchema._
import com.bio4j.dynamograph.model.taxonomy.NcbiTaxonomySchema._


object TaxonomyWriters {

  case object TaxonWriter             extends VertexWriter(NcbiTaxon, TaxonTable)
  case object RankWriter              extends VertexWriter(NcbiRank, RankTable)

  case object ParentEdgeWriter        extends EdgeWriter(Parent, ParentTables)
  case object AssignedRankEdgeWriter  extends EdgeWriter(AssignedRank, AssignedRankTables)
  case object SubrankEdgeWriter       extends EdgeWriter(SubRank, SubrankTables)

  val vertexWritersMap = Map[String, AnyVertexWriter] (
    NcbiTaxonType.label           -> TaxonWriter,
    NcbiRankType.label            -> RankWriter
  )

  val edgeWritersMap = Map[String, AnyEdgeWriter] (
    ParentType.label              -> ParentEdgeWriter,
    AssignedRankType.label        -> AssignedRankEdgeWriter,
    SubrankType.label             -> SubrankEdgeWriter
  )

}
