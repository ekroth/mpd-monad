package mpd.messages

import scala.concurrent.Future

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
