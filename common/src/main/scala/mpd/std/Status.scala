package mpd
package std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.messages.{ StatusMsg, Status, State => MState, ExecutorComponent, SubSystem }
import mpd.messages.SubSystem._
import mpd.util.MpdParse

trait StatusMsgStd extends StatusMsg {
  this: ExecutorComponent =>
  import scalaz._
  import Scalaz._

  override def clearError() = raw("clearerror")

  override def currentSong() = wread("currentsong") map { x =>
    val song = MpdParse.parseSong(MpdParse.mapValues(x))
    if (song.file.isDefined) song.some
    else none
  }

  override def idle(xs: Set[SubSystem]) = wread(s"""idle ${xs.mkString(" ")}""") map { x =>
    (x flatMap { l =>
      val reg = "changed: (.*)".r
      l match {
	case reg(s) => SubSystem.withName(s).some
	case _ => None
      }
    }).toSet
  } 

  override def status() = wread("status") map { x =>
    val s = MpdParse.mapValues(x)

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
      MState.withName(s("state").head),
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
