import sbt._
import Keys._

object build extends Build {

  lazy val mpdSettings = Defaults.defaultSettings ++ Seq(
    scalaVersion := "2.10.1",
    scalacOptions ++= Seq("-unchecked", "-deprecation")
  ) 

    lazy val common = Project(
      "mpd-common",
      file("common"),
      settings = build.mpdSettings ++ Seq(
	libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.0",
	resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
	libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2-SNAPSHOT")
    )

    lazy val web = {
      import play.Project._
        val appName         = "mpd-web"
        val appVersion      = "1.0-SNAPSHOT"

        val appDependencies = Seq(
            // Add your project dependencies here,
            jdbc,
            anorm
        )


        play.Project(appName, appVersion, appDependencies).settings(    
        ) dependsOn(common)
    }
}
