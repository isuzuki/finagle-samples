lazy val commonSettings = Seq(
  organization := "io.github.isuzuki",
  name := "finagle-sample",
  version := "0.1",
  scalaVersion := "2.12.6",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-target:jvm-1.8"
  )
)

lazy val finagleSample = project.in(file("."))
  .settings(moduleName := "root")
  .settings(commonSettings)
  .settings(Seq(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % "18.7.0"
    )
  ))
