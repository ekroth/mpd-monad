package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._

trait PlaylistMessagesStd extends PlaylistMessages {
  import scalaz._
  import Scalaz._

  override def currentsong() = wread("currentsong") map {
    x =>
      try {
        for (v <- x) yield {
          val r = """(.*): (.*)""".r
          val s = (r.findAllMatchIn(v.mkString("\n")) map {
            str: scala.util.matching.Regex.Match => (str.group(1).toLowerCase -> str.group(2))
          }).toMap

          println(s)

          CurrentSong(
            s("file"),
            s("time").toInt,
            s("album"),
            s("artist"),
            s("title"),
            s("track").toInt,
            s("pos").toInt,
            s("id").toInt)
        }
      } catch {
        case e: Throwable => Unknown("invalid response, currentsong").left
      }
  }

}
