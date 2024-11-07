ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.2"

lazy val root = (project in file("."))
  .settings(name := "caliban-demo")

val calibanVersion = "2.9.0"

libraryDependencies ++= Seq(
  "com.github.ghostdogpr" %% "caliban" % calibanVersion,
  "com.github.ghostdogpr" %% "caliban-zio-http" % calibanVersion,
  "com.softwaremill.sttp.client3" %% "zio" % "3.9.7"
)

scalacOptions ++= Seq(
  "-deprecation", // emit warning and location for usages of deprecated APIs
  "-explain", // explain errors in more detail
  "-explain-types", // explain type errors in more detail
  "-feature", // emit warning and location for usages of features that should be imported explicitly
  "-indent", // allow significant indentation.
  "-new-syntax", // require `then` and `do` in control expressions.
  "-print-lines", // show source code line numbers.
  "-unchecked", // enable additional warnings where generated code depends on assumptions
  "-Xkind-projector", // allow `*` as wildcard to be compatible with kind projector
  "-Werror", // fail the compilation if there are any warnings
  "-Xmigration", // warn about constructs whose behavior may have changed since version
  "-explain-cyclic",
)
