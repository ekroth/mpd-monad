package mpd.messages

import scala.concurrent.Future

import mpd.Server
import mpd.Result._
import mpd.Server._

object Playback extends PlaybackTypes

trait PlaybackTypes {
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

    override def crossfade(i: Int) = (actor ask Crossfade(i)).mapTo[OKResult]
    override def next() = (actor ask Next()).mapTo[OKResult]
    override def pause(p: Boolean) = (actor ask Pause(p)).mapTo[OKResult]
    override def play(i: Int) = (actor ask Play(i)).mapTo[OKResult]
  }
}
