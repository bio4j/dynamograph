package com.bio4j.dynamograph.parser

import scala.io.Source
import scala.collection.JavaConversions
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import scala.annotation.tailrec
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.taxonomy.NcbiTaxonomySchema.{ParentType, AssignedRankType, SubrankType}
import com.amazonaws.services.cloudfront.model.InvalidArgumentException

class TaxonomyParser(val nodesSrc: Source,val namesSrc: Source, val rankSrc : Source) extends AnyGoParser {

  val nameIt = namesSrc.getLines()

  override def foreach[U](f: (SingleElement) => U): Unit = {

    val ranks = parseRanks(rankSrc.getLines)
    ranks.foreach(x => f(toSingleElement(ranks, x)))
    for (line <- nodesSrc.getLines){
        f(processLine(line))
    }
  }

  private def parseRanks(lines : Iterator[String]) : Map[Int,String] = {

    @tailrec
    def parserRanksHelper(lines: Iterator[String], result : Map[Int,String]) : Map[Int,String] = {
      if (!lines.hasNext) result
      else {
        val line = lines.next
        parserRanksHelper(lines, result +  parseRankEntry(line))
      }
    }

    parserRanksHelper(lines,Map())
  }

  def toSingleElement(ranks: Map[Int, String],rank: (Int, String)) : SingleElement = {
    val vertex = Map(name.label -> rank._2, number.label -> rank._1.toString)
    val relations = ranks.filterKeys(_ > rank._1).map(x =>
      Map(targetId.label -> x._2.toString, ParsingContants.relationType -> SubrankType.label)
    ).toList
    SingleElement(vertex, relations)
  }

  private def parseRankEntry(line: String) = {
    val splitResult = line.split("\\")
    splitResult(0).trim.toInt -> splitResult(1).trim
  }

  private def processLine(line : String) : SingleElement = {
//    Map[String, AttributeValue] =
    def scientific(id: String, it : Iterator[String]) : String = {
      val tId = id.toInt
      val nameColumns = it.next().split("\\|")
      if (nameColumns(0).trim().equals(id)) throw new InvalidArgumentException("")
      nameColumns(1)
    }
    val columns = line.split("\\|");
    val id = columns(0).trim
    val parentId = columns(1).trim
    val rank = columns(2).trim
    val taxComment = if (columns.length < 13) ""  else columns(12).trim
    val sName = scientific(id, nameIt)
    val vertex = Map(id.label -> id, comment.label -> taxComment, scientificName.label -> sName)
    val relationships = List(Map(targetId.label -> parentId, ParsingContants.relationType -> ParentType.label),
                             Map(targetId.label -> rank,     ParsingContants.relationType -> AssignedRankType.label))
    SingleElement(vertex, relationships)
  }
}
