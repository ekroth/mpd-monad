package mpd
package std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.messages.{ PlaybackMsg, ExecutorComponent }

/** PlaybackMsg
 * Status: Completed
 */
trait PlaybackMsgStd extends PlaybackMsg {
  this: ExecutorComponent =>
  import scalaz._
  import Scalaz._

  override def next() = raw("next")
  
  override def pause(p: Option[Boolean]) =
    raw("pause", s"${p.getOrElse(false).toMpd}")
  
  override def play(i: Option[Int]) =
    raw("play", s"${i.getOrElse(-1)}")
  
  override def playid(i: Option[Int]) = 
    raw("playid", s"${i.getOrElse(0)}")
  
  override def previous() = raw("previous")

  override def seek(pos: Int, time: Int) = raw("seek", s"$pos", s"$time")

  override def seekid(id: Int, time: Int) = raw("seekid", s"$id", s"$time")
  
  override def seekcur(time: Int) = raw("seekcur", s"$time")
  
  override def stop() = raw("stop")
}
