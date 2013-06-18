package mpd.messages

import scala.concurrent.Future

import mpd.Result._

case class Crossfade(i: Int)
case class Next()
case class Pause(p: Boolean)
case class Play(i: Int)

trait PlaybackMessages extends ServerMessages {
  def crossfade(i: Int): Future[OKResult]
  def next(): Future[OKResult]
  def pause(p: Boolean): Future[OKResult]
  def play(i: Int): Future[OKResult]

  abstract override def supported = super.supported ++ Set(
    "crossfade",
    "next",
    "pause",
    "play")
}

trait PlaybackActorMessages extends PlaybackMessages {
  self: ActorComponent =>

  override def crossfade(i: Int) = ask[OKResult](Crossfade(i))
  override def next() = ask[OKResult](Next())
  override def pause(p: Boolean) = ask[OKResult](Pause(p))
  override def play(i: Int) = ask[OKResult](Play(i))
}
