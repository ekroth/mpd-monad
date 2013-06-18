package mpd.messages

import scala.concurrent.Future

import mpd.Result._

object PlaybackPackets {
  case class Crossfade(i: Int)
  case class Next()
  case class Pause(p: Boolean)
  case class Play(i: Int)
}

import PlaybackPackets._

trait PlaybackMessages extends ServerMessages {
  def crossfade(i: Int): Future[DefaultOK]
  def next(): Future[DefaultOK]
  def pause(p: Boolean): Future[DefaultOK]
  def play(i: Int): Future[DefaultOK]

  abstract override def required = super.required ++ Set(
    "crossfade",
    "next",
    "pause",
    "play")
}

trait PlaybackActorMessages extends PlaybackMessages {
  self: ActorComponent =>

  override def crossfade(i: Int) = ask[DefaultOK](Crossfade(i))
  override def next() = ask[DefaultOK](Next())
  override def pause(p: Boolean) = ask[DefaultOK](Pause(p))
  override def play(i: Int) = ask[DefaultOK](Play(i))
}
