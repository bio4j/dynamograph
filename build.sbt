Nice.scalaProject

organization := "bio4j"

name := "dynamograph"

description := "dynamograph project"

bucketSuffix := "era7.com"

libraryDependencies ++= Seq(
  "ohnosequences" %% "scarph" % "0.1.0-SNAPSHOT",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)
