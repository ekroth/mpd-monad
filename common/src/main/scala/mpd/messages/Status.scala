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

case class Status(volume: Int, repeat: Int, random: Int, single: Int, consume: Int, playlist: Int, playlistlength: Int, xfade: Int, mixrampdb: String, mixrampdelay: String, state: State, song: Int, songid: Int, time: String, elapsed: String, bitrate: Int, audio: String, nextsong: Int, nextsongid: Int)

case class CurrentSong(file: String, lastModified: String, time: Int, title: String, artist: String, album: String, albumArtist: String, genre: String, date: String, composer: String, disc: String, track: String, pos: Int, id: Int)

trait StatusMsg extends ServerMsg {
  // clearerror

  /** Displays the metadata of the current song. */
  def currentsong(): Future[DefaultT[Option[CurrentSong]]]

  // idle [SUBSYSTEMS...]

  def status(): Future[DefaultT[Status]]

  // stats

  abstract override def required = super.required ++ Set()
}
