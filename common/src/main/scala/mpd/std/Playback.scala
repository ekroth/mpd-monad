package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._


trait PlaybackMessagesStd extends PlaybackMessages {
  import scalaz._
  import Scalaz._

  override def crossfade(i: Int) = raw(s"crossfade $i") map { _ => OK().right }
  override def stop() = raw("stop") map { _ => OK().right }
  override def previous() = raw("previous") map { _ => OK().right }
  override def next() = raw("next") map { _ => OK().right }
  override def pause(p: Option[Boolean]) = 
    raw("pause" + p.map(" " + _.toMpd).getOrElse("")) map { _ => OK().right }
  override def play(i: Option[Int]) = 
    raw("play" + i.map(" " + _).getOrElse("")) map { _ => OK().right }
}
