package bio4j.dynamograph

import com.bio4j.dynamograph.model.go.TableModelInitializer

object Dynamograph {
  def main(args: Array[String]) {
    TableModelInitializer.clear()
  }
}