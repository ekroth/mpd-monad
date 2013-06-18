import sbt._
import Keys._

object build extends Build {
  val projName = "mpd"

  val mpdSettings = Defaults.defaultSettings ++ Seq(
    scalaVersion := "2.10.1",
    scalacOptions ++= Seq("-unchecked", "-deprecation")
  ) 

    val common = Project(
      projName + "-common",
      file("common"),
      settings = build.mpdSettings ++ Seq(
	libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.0",
	resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
	libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2-SNAPSHOT")
    )

    val web = {
      import play.Project._
        val appVersion      = "1.0-SNAPSHOT"

        val appDependencies = Seq(
            // Add your project dependencies here,
            jdbc,
            anorm
        )


        play.Project(projName + "-web", appVersion, appDependencies,path = file("web")).settings() //.dependsOn(common)
    }
}
