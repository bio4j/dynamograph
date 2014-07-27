Nice.scalaProject

organization := "bio4j"

name := "dynamograph"

description := "dynamograph project"

bucketSuffix := "era7.com"

scalaVersion := "2.11.1"

skip in update := true

libraryDependencies ++= Seq(
  "ohnosequences" %% "scarph" % "0.2.0-SNAPSHOT",
  "ohnosequences" %% "tabula" % "0.1.0-SNAPSHOT",
  //"com.typesafe.scala-logging" %% "scala-logging-slf4j" % "3.0.0", currentlu unavailable for 2.11
  "com.amazonaws" % "aws-java-sdk" % "1.8.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test"
)

dependencyOverrides ++= Set(
  "ohnosequences" %% "type-sets" % "0.5.0-SNAPSHOT",
  "org.apache.httpcomponents" % "httpclient" % "4.2",
  "commons-codec" % "commons-codec" % "1.7",
  "com.amazonaws" % "aws-java-sdk" % "1.8.0",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.2",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
  "joda-time" % "joda-time" % "2.3"
)
