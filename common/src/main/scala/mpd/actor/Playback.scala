package mpd.actor

import mpd.messages._

object PlaybackPackets {
  case class Crossfade(i: Int)
  case class Next()
  case class Pause(p: Boolean)
  case class Play(i: Int)
}

import PlaybackPackets._
trait PlaybackMessagesActor extends PlaybackMessages {
  self: ActorComponent =>

  override def crossfade(i: Int) = ask[DefaultOK](Crossfade(i))
  override def next() = ask[DefaultOK](Next())
  override def pause(p: Boolean) = ask[DefaultOK](Pause(p))
  override def play(i: Int) = ask[DefaultOK](Play(i))
}
