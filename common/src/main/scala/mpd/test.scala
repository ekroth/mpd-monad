package mpd

import java.io._
import java.net._

object SocketTest {
  val StartBulk = "command_list_begin"
  val EndBulk = "command_list_end"

  def logy[T](s: String)(f: => T) = {
    println(s)
    f
  }

  def say(s: String) {
    val ia = logy("ia") { InetAddress.getByName("localhost") }
    val socket = new Socket(ia, 6600)

    val out = logy("open output") { new OutputStreamWriter(socket.getOutputStream(), "UTF-8") }

    logy("write out") { out.write(StartBulk + "\n" + s + EndBulk + "\n") }
    logy("flush out") { out.flush }

    logy("close out") { out.close }
    logy("close socket") { socket.close }
    println("done")
  }
}
