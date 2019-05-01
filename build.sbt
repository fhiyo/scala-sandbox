name := "scala-sandbox"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
