import mpd.commands.Result._
import mpd.commands._

trait AdminCommands extends Commands {
  def disableoutput(id: Int): OKResult
  def enableoutput(id: Int): OKResult
  def kill(): PossibleACK
  def update(path: Option[String]) = ???

  private[this] val commands = Set(
    "disableoutput",
    "enableoutput",
    "kill",
    "update")

  abstract override def reqs = super.reqs ++ commands
}

