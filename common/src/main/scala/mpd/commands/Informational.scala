import mpd.commands.Result._
import mpd.commands._

trait Informational extends Commands {
  def commands: Result[List[String], ACK]

  private[this] val reqCmds = Set(
    "disableoutput",
    "enableoutput",
    "kill",
    "update")

  abstract override def reqs = super.reqs ++ reqCmds
}
