package mpd.messages

import scala.concurrent.Future

object State extends Enumeration {
  type State = Value
  val Play, Stop, Pause = Value

  def apply(s: String) = s match {
    case "play" => Play
    case "stop" => Stop
    case "pause" => Pause
  }
}

import State._

case class Status(
  volume: Int, repeat: Int, 
  random: Int, single: Int, 
  consume: Int, playlist: Int, 
  playlistlength: Int, 
  xfade: Int, mixrampdb: String, 
  mixrampdelay: String, state: State, 
  song: Int, songid: Int, 
  time: String, elapsed: String, 
  bitrate: Int, audio: String,
  nextsong: Int, nextsongid: Int)

object Song {
  val params = 14
}

case class Song(
  file: Option[String], lastModified: Option[String], 
  time: Option[Int], title: Option[String], 
  artist: Option[String], album: Option[String], 
  albumArtist: Option[String], genre: Option[String], 
  date: Option[String], composer: Option[Seq[String]], 
  disc: Option[String], track: Option[String],
  pos: Option[Int], id: Option[Int])

trait StatusMsg extends ServerMsg {
  // clearerror

  /** Displays the metadata of the current song. */
  def currentsong(): Future[DefaultT[Option[Song]]]

  // idle [SUBSYSTEMS...]

  def status(): Future[DefaultT[Status]]

  // stats

  abstract override def required = super.required ++ Set()
}
