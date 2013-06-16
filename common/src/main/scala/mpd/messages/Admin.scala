package mpd.messages

import mpd.Server

import mpd.Result._
import mpd.ServerComponent


object Admin extends AdminTypes

trait AdminTypes {
  case class DisableOutput(id: Int)
}

trait AdminComponent extends ServerComponent {
  def disableoutput(id: Int): OKResult
  def enableoutput(id: Int): OKResult
  def kill(): PossibleACK
  def update(path: Option[String]): Unit

  abstract override def supported = super.supported ++ Set(
    "disableoutput",
    "enableoutput",
    "kill",
    "update")
}
