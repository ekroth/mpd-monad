package mpd.actor

import scala.{ PartialFunction => PF }

import akka.actor._

import mpd.{ MPDConnection => MPDC }
import mpd.messages._

class StdActor extends Actor {

  def receive = {
    case _ => None
  }
}

trait MessageHandler {
  
  /*def apply(mpd: MPDC, msg: Any) = {
    case x => sys.error(s"invalid msg: $x")
  }*/
}

/*trait PlaybackMessageHandler extends MessageHandler {
  import mpd.messages.PlaybackPackets._

  abstract override def apply(mpd: MPDC, msg: Any) = msg match {
    case Next() => mpd.writef("next")
    case Pause(v) => mpd.writef(s"pause ${v.toMpd}")
    case _ => super.apply(mpd, msg)
  }
}
*/
