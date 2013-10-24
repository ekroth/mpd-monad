package mpd

trait SoundCloudFunctions {
  this: BaseFunctions =>

  import BaseInstances._
  import SoundCloudInstances._

  /** load track by id */
  def loadSCTrack(id: Int): MPD[Unit] = for {
    _ <- clbegin
    _ <- writeln("load " + scTrack(id.toString))
    _ <- clend
  } yield ()

  /** load playlist by id */
  def loadSCPlaylist(id: Int): MPD[Unit] = for {
    _ <- clbegin
    _ <- writeln("load " + scPlaylist(id.toString))
    _ <- clend
  } yield ()

  def scPlaylist(id: String) = s"$URI://playlist/$id"
  def scTrack(id: String) =  s"$URI://track/$id"  
  def scURL(url: String) = s"$URI://url/$url"
}

final object SoundCloudFunctions 
      extends SoundCloudFunctions
      with BaseFunctions

trait SoundCloudInstances {
  val URI = "soundcloud"
}

final object SoundCloudInstances extends SoundCloudInstances
