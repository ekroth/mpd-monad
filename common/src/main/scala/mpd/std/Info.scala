package mpd.std

import scala.concurrent.ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._
import mpd.messages.{ State => MState }
import mpd.util._

trait InfoMessagesStd extends InfoMessages {
  import scalaz._
  import Scalaz._

  override def status() = wread("status") map {
    x =>
      try {
        for (v <- x) yield {
          val s = MpdParse.mapValues(v)

            Status(
              s("volume").toInt,
	      s("repeat").toInt,
	      s("random").toInt,
	      s("single").toInt,
	      s("consume").toInt,
	      s("playlist").toInt,
	      s("playlistlength").toInt,
	      s("xfade").toInt,
	      s("mixrampdb"),
	      s("mixrampdelay"),
	      MState(s("state")),
	      s("song").toInt,
	      s("songid").toInt,
	      s("time"),
	      s("elapsed"),
	      s("bitrate").toInt,
	      s("audio"),
	      s("nextsong").toInt,
	      s("nextsongid").toInt)
        }
      } catch {
        case e: Throwable => Unknown(s"(status()), error parsing: $e").left
      }
  }

}
