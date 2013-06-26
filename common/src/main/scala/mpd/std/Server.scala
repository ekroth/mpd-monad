package mpd
package std

import scala.concurrent._
import ExecutionContext.Implicits.global

import messages.{ ServerMsg, ExecutorComponent }

trait ServerMsgStd extends ServerMsg {
  self: ExecutorComponent with MpdComponent =>

  override def raw(s: String) = future {
    mpd.con.writef(s)
  }

  override def read() = future {
    mpd.con.read
  }

  override def clear() = future {
    mpd.con.clear
  }

  override def required = Set.empty[String]
}
