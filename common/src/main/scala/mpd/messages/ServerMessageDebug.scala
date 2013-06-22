package mpd.messages

import scala.concurrent._

import ExecutionContext.Implicits.global

trait ServerMessagesDebug extends ServerMessages {

  abstract override def raw(s: String) = {
    println(s"raw($s)")
    super.raw(s)
  }

  abstract override def read() =
    for {
      v <- super.read()
      _ = println(s"read() = $v")
    } yield v
}
