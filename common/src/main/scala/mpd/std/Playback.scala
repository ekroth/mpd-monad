package mpd
package std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.messages.{ PlaybackMsg, ExecutorComponent }

trait PlaybackMsgStd extends PlaybackMsg {
  this: ExecutorComponent =>
  import scalaz._
  import Scalaz._

  override def next() = raw("next")
  
  override def pause(p: Option[Boolean]) =
    raw("pause" + p.map(" " + _.toMpd).getOrElse(""))
  
  override def play(i: Option[Int]) =
    raw("play" + i.map(" " + _).getOrElse(""))
  
  override def previous() = raw("previous")
  
  override def stop() = raw("stop")
}
