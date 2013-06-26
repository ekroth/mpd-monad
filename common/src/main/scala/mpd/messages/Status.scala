package mpd
package messages

import scala.concurrent.Future

import Result._

object State extends Enumeration {
  type State = Value
  val play, stop, pause = Value
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

object SubSystem extends Enumeration {
  type SubSystem = Value
  val database, update, stored_playlist, 
      playlist, player, mixer, 
      output, options, sticker, 
      subscription, message = Value
}

import SubSystem._

trait StatusMsg extends ServerMsg {
  this: ExecutorComponent =>
  // clearerror

  /** Displays the metadata of the current song. */
  def currentsong(): Future[DefaultT[Option[Song]]]

  // idle [SUBSYSTEMS...]
  /** Returns a Seq of which subsystems that were updated */
  def idle(xs: SubSystem*): Future[DefaultT[Seq[SubSystem]]]

  def status(): Future[DefaultT[Status]]

  // stats

  abstract override def required = super.required ++ Set()
}
