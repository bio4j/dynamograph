Nice.scalaProject

organization := "bio4j"

name := "dynamograph"

description := "dynamograph project"

bucketSuffix := "era7.com"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "ohnosequences" %% "scarph" % "0.1.0-SNAPSHOT",
  "com.amazonaws" % "aws-java-sdk" % "1.7.9",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)

dependencyOverrides ++= Set(
  "org.apache.httpcomponents" % "httpclient" % "4.2",
  "commons-codec" % "commons-codec" % "1.7",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.2",
  "joda-time" % "joda-time" % "2.3"
)
