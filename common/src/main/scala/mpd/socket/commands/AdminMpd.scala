package mpd.socket.commands

import mpd.commands._

object AdminMpd {
  private[commands] val reqCmds = Set(
    "disableoutput",
    "enableoutput",
    "kill",
    "update")
}

trait AdminMpd extends Admin {
  override def disableoutput(id: Int) = ???
  override def enableoutput(id: Int) = ???
  override def kill() = ???
  override def update(path: Option[String]) = ???

  abstract override def reqs = super.reqs ++ AdminMpd.reqCmds
}
