package bio4j.dynamograph

import com.bio4j.dynamograph.ServiceProvider
import com.bio4j.dynamograph.model.Properties._
import com.bio4j.dynamograph.model.go.GoImplementation.GoTerm
import ohnosequences.scarph._, ops.default._
import ohnosequences.scarph.ops.default._
import com.bio4j.dynamograph.model.go.GoSchema.GoTermType
import com.bio4j.dynamograph.model.go.TableModelInitializer

object Dynamograph {
  def main(args: Array[String]) {
    TableModelInitializer.initialize()
  }
}