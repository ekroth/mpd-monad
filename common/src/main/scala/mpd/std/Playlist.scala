package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._
import mpd.util._

trait PlaylistMessagesStd extends PlaylistMessages {
  import scalaz._
  import Scalaz._

  override def currentsong() = wread("currentsong") map {
    x =>
      try {
        for (v <- x) yield {
          val s = MpdParse.mapValues(v)

          if (s.isEmpty) None else
            Some(CurrentSong(
              s("file"),
              s("time").toInt,
              s("album"),
              s("artist"),
              s("title"),
              s("track").toInt,
              s("pos").toInt,
              s("id").toInt))
        }
      } catch {
        case e: Throwable => Unknown(s"(status()), error parsing $e").left
      }
  }

}
