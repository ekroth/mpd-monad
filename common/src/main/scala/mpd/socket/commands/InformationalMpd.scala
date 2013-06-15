package mpd.socket.commands

import mpd.Result._
import mpd.commands._

object InformationalMpd {
  private[commands] val reqCmds = Set("commands")
}

trait InformationalMpd extends Informational {
  override def commands: Result[List[String], ACK] = ???

  abstract override def reqs = super.reqs ++ InformationalMpd.reqCmds
}
