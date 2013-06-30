package mpd
package messages

import scala.concurrent.Future

object State extends Enumeration {
  type State = Value
  val play, stop, pause = Value
}

import mpd.messages.State._

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

object SubSystem extends Enumeration {
  type SubSystem = Value
  val database, update, stored_playlist, 
      playlist, player, mixer, 
      output, options, sticker, 
      subscription, message = Value
}

import mpd.messages.SubSystem._

trait StatusMsg extends ServerMsg {
  this: ExecutorComponent =>
  def clearError(): Future[Unit]

  /** Displays the metadata of the current song. */
  def currentSong(): Future[Option[Song]]

  /** Returns a Seq of which subsystems that were updated */
  def idle(xs: Set[SubSystem]): Future[Seq[SubSystem]]

  final def idle(): Future[Seq[SubSystem]] = idle(SubSystem.values)

  def status(): Future[Status]

  // stats

  abstract override def required = super.required ++ Set()
}
