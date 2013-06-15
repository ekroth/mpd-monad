import sbt._
import Keys._

object build extends Build {
  lazy val common = Project(id = "mpd-monad",
                            base = file("common"))

  lazy val web = Project(id = "mpd-web",
                         base = file("web")) dependsOn(common)
}
