name := """simpleDI24"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  ws,
  specs2 % Test,
  "org.mockito" % "mockito-core" % "1.9.5" % Test
)

routesGenerator := InjectedRoutesGenerator
