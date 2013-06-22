package mpd.messages

import scala.concurrent.Future

trait PlaybackMessages extends ServerMessages {
  def crossfade(i: Int): Future[DefaultOK]
  def stop(): Future[DefaultOK]
  def previous(): Future[DefaultOK]
  def next(): Future[DefaultOK]
  def pause(p: Option[Boolean]): Future[DefaultOK]
  def pause(): Future[DefaultOK] = pause(None)
  def play(i: Option[Int]): Future[DefaultOK]
  def play(): Future[DefaultOK] = play(None)

  abstract override def required = super.required ++ Set(
    "crossfade",
    "next",
    "pause",
    "play")
}
