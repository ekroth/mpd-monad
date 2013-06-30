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
    raw(s"pause ${p.getOrElse(false).toMpd}")
  
  override def play(i: Option[Int]) =
    raw(s"play ${i.getOrElse(-1)}")
  
  override def playid(i: Option[Int]) = 
    raw(s"playid ${i.getOrElse(0)}")
  
  override def previous() = raw("previous")

  override def seek(pos: Int, time: Int) = raw(s"seek $pos $time")

  override def seekid(id: Int, time: Int) = raw(s"seekid $id $time")
  
  override def seekcur(time: Int) = raw(s"seekcur $time")
  
  override def stop() = raw("stop")
}
