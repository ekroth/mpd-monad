package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._
import mpd.messages.{ State => MState }
import mpd.util._

trait StatusMsgStd extends StatusMsg {
  import scalaz._
  import Scalaz._

  override def currentsong() = wread("currentsong") map { x => 
    for { v <- x
	  s <- MpdParse.parseSong(MpdParse.mapValues(v)) } yield s
  }

  override def status() = wread("status") map {
    x =>
      try {
        for (v <- x) yield {
          val s = MpdParse.mapValues(v)

          Status(
            s("volume").head.toInt,
            s("repeat").head.toInt,
            s("random").head.toInt,
            s("single").head.toInt,
            s("consume").head.toInt,
            s("playlist").head.toInt,
            s("playlistlength").head.toInt,
            s("xfade").head.toInt,
            s("mixrampdb").head,
            s("mixrampdelay").head,
            MState(s("state").head),
            s("song").head.toInt,
            s("songid").head.toInt,
            s("time").head,
            s("elapsed").head,
            s("bitrate").head.toInt,
            s("audio").head,
            s("nextsong").head.toInt,
            s("nextsongid").head.toInt)
        }
      } catch {
        case e: Throwable => Unknown(s"(status()), error parsing: $e").left
      }
  }

}
