import sbt._
import Keys._

object build extends Build {
  val projName = "mpd"
  val sharedSettings = Seq(
    scalaVersion := "2.10.1",
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"))

  val common = {
    val name = "common"

    Project(
      projName + "-" + name,
      file(name),
      settings = Defaults.defaultSettings ++ sharedSettings ++ Seq(
	resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
	libraryDependencies ++= Seq("org.scalaz" %% "scalaz-core" % "7.0.0",
				    "com.typesafe.akka" %% "akka-actor" % "2.1.0",
				    "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"),
	initialCommands += """import mpd._ 
			      import AllInstances._
			      //import implicitly[Base]._"""
	)
      )
  }

  val web = {
    import play.Project._
    val name = "web"
    val appVersion = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,1
      jdbc,
      anorm)

    play.Project(projName + "-" + name, appVersion, appDependencies, path = file(name)).settings(sharedSettings:_*).dependsOn(common)
  }
}
