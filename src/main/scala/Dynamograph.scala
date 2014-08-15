package bio4j.dynamograph

import com.bio4j.dynamograph.ServiceProvider
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.go.GoImplementation.{IsA, GoTerm}
import ohnosequences.scarph._, ops.default._
import ohnosequences.scarph.ops.default._
import com.bio4j.dynamograph.model.go.GoSchema.{IsAType, GoTermType}
import com.bio4j.dynamograph.model.go.TableModelInitializer
import com.bio4j.dynamograph.model.go.GoImplementation.GoTerm._


object Dynamograph {
  def main(args: Array[String]) {
    TableModelInitializer.initialize()
//    val g = GoTerm ->> ServiceProvider.dao.get("GO:0000001",GoTermType)
//    println(g.get(id))
//    println(g.get(name))
//    val i = g.out(IsA)
//    println(i)
//    for (x <- i){
//      println (x)
//    }

  }
}