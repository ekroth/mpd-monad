package mpd
package std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import messages.{ StatusMsg, Status, State => MState, ExecutorComponent }
import mpd.util.MpdParse

trait StatusMsgStd extends StatusMsg {
  this: ExecutorComponent =>
  import scalaz._
  import Scalaz._

  override def currentsong() = wread("currentsong") map { x =>
   for (v <- x) yield {
      val song = MpdParse.parseSong(MpdParse.mapValues(v))
      if (song.file.isDefined) song.some
      else none
    }
  }

  override def status() = for (x <- wread("status")) yield {
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
  }
}
