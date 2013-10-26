import sbt._
import Keys._
import AndroidKeys._

object build extends Build {
  val projName = "mpd"
  val sharedSettings = Seq(
    scalaVersion := "2.10.1",
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"))

  lazy val common = {
    val name = "common"

    Project(
      projName + "-" + name,
      file(name),
      settings = Defaults.defaultSettings ++ sharedSettings ++ Seq(
	resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/snapshots/",
	libraryDependencies ++= Seq("org.scalaz" %% "scalaz-core" % "7.0.0",
	  "com.typesafe.akka" %% "akka-actor" % "2.1.0",
	  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"),
	initialCommands +=
	  """import mpd._
	  object AllInstancesDebug extends AllInstances with DebugInstances
	  import AllInstancesDebug._
	
	  println("usage: \"useCon { c => (scImplicit.load(SCURL(https://soundcloud.com/steve-cobby/heeds))).run(c) }\"")

	  val con = (implicitly[Base]).Connect("127.0.0.1", 6600)
	  def useCon[T](f: mpd.MPDS => T) = for { m <- con } yield f(m)"""
      )
    )
  }

  final object General {    
    val name = "mpdm-droid"
    val settings = Defaults.defaultSettings ++ sharedSettings ++ Seq (
      version := "0.1",
      versionCode := 0,
      platformName in Android := "android-17",
      libraryDependencies ++= Seq(
        "org.scaloid" %% "scaloid" % "2.3-8")
    )
    val proguardSettings = Seq (
      useProguard in Android := true,
      proguardOptimizations in Android := Seq(
        "-dontoptimize",
        "-keep class com.github.ekroth.mpdmonad.**",
        "-keep class com.github.ekroth.mpdmonad.*",
        """-keep class scala.collection.SeqLike {
    public protected *;
        }""")
    )

    val fullAndroidSettings =
      General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    AndroidManifestGenerator.settings
  }

  lazy val mpdDroid = Project (
    General.name,
    file("android"),
    settings = General.fullAndroidSettings ++ General.proguardSettings
  ).dependsOn(common)

  lazy val mpdDroidFast = Project (
    General.name + "-fast",
    file("android"),
    settings = General.fullAndroidSettings ++ Seq(useProguard in Android := false)
  ).dependsOn(common)
  
  lazy val web = {
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
