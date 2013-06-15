import sbt._
import Keys._

object build extends Build {
  lazy val common = Project(id = "mpd-monad",
                            base = file("common"))

  /*lazy val android = Project(id = "mpd-monad-android",
                         base = file("android")) dependsOn(common)*/
}
