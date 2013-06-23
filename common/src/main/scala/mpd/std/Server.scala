package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._

trait MpdComponent {
  def mpd: BasicMpd

  trait BasicMpd {
    def con: MPDC
    def connect(addr: String, port: Int): PossibleError
    def disconnect(): Unit
  }
}

trait ServerMessagesStd extends ServerMessages {
  self: MpdComponent =>

  override def raw(s: String) = future {
    mpd.con.writef(s)
  }

  override def read() = future {
    mpd.con.read
  }

  override def required = Set.empty[String]
}
