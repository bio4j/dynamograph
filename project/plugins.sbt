resolvers ++= Seq(
  "Era7 maven releases" at "http://releases.era7.com.s3.amazonaws.com"
)

addSbtPlugin("ohnosequences" % "nice-sbt-settings" % "0.4.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

