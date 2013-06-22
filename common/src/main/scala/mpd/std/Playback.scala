package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._


trait PlaybackMessagesStd extends PlaybackMessages {
  import scalaz._
  import Scalaz._

  override def crossfade(i: Int) = raw(s"crossfade $i") map { _ => OK().right }
  override def next() = raw("next") map { _ => OK().right }
  override def pause(p: Boolean) = raw("pause") map { _ => OK().right }
  override def play(i: Int) = raw("play") map { _ => OK().right }
}
