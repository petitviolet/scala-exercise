
val libVersion = "1.0"

val scala = "2.12.1"

def commonSettings(name: String) = Seq(
  scalaVersion := scala,
  version := "1.0",
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

lazy val root = (project in file("."))
  .settings(commonSettings("scala-exercize"))


lazy val medium = (project in file("modules/medinum"))
  .settings(commonSettings("medium"))

