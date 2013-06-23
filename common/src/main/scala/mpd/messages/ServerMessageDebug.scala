package mpd.messages

import scala.concurrent._

import ExecutionContext.Implicits.global

trait ServerMessagesDebug extends ServerMessages {
  def limit(s: String) = {
    val max = 64
    val l = s.length

    if (l <= max) s
    else s.substring(0, max / 2) + "..." + s.substring(l - max / 2)
  }

  abstract override def raw(s: String) = {
    println(s"raw($s)")
    super.raw(s)
  }

  abstract override def read() = {
    println("read()")
    val s = System.currentTimeMillis
    for {
      v <- super.read()
      _ = println(s"value = ${limit(v.toString)}\ntime: ${System.currentTimeMillis - s}")
    } yield v
  }
}
