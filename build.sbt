name := """tp1-redes-neuronales"""

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "org.beykery" % "neuroph" % "2.92"

libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.0.0"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.4"

//libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

javaOptions in ThisBuild ++= Seq("-Xmx2g")