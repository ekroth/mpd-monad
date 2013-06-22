package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._


trait PlaybackMessagesStd extends PlaybackMessages {
  import scalaz._
  import Scalaz._

  override def crossfade(i: Int): Future[DefaultOK] = ???
  override def next(): Future[DefaultOK] = ???
  override def pause(p: Boolean) = raw("pause") map { 
    _ match {
      case \/-(_) => OK().right
      case -\/(x: Error) => x.left
    }
  }

  override def play(i: Int): Future[DefaultOK] = ???
}
