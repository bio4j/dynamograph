Nice.scalaProject

organization := "bio4j"

name := "dynamograph"

description := "dynamograph project"

bucketSuffix := "era7.com"

scalaVersion := "2.11.1"

skip in update := true

resolvers ++= Seq(
  "Sonatype OSS Releases"  at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "ohnosequences" %% "scarph" % "0.2.0-SNAPSHOT",
  "ohnosequences" %% "tabula" % "0.1.0-SNAPSHOT",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "com.amazonaws" % "aws-java-sdk" % "1.8.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
  "com.casualmiracles" %% "treelog" % "1.2.3",
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test"
)

dependencyOverrides ++= Set(
  "ohnosequences" %% "type-sets" % "0.4.99-SNAPSHOT",
  "org.apache.httpcomponents" % "httpclient" % "4.2",
  "commons-codec" % "commons-codec" % "1.7",
  "com.amazonaws" % "aws-java-sdk" % "1.8.0",
  "org.slf4j" % "slf4j-api" % "1.7.7",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.1.2",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.1.2",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
  "joda-time" % "joda-time" % "2.3"
)
