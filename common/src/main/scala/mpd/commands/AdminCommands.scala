import mpd.commands.Result._
import mpd.commands.Satisfied

trait AdminCommands {
  def disableoutput(id: Int): OKResult
  def enableoutput(id: Int): OKResult
  def kill(): PossibleACK
  def update(path: Option[String]) = ???

  implicit val adminSatisfied = new Satisfied[AdminCommands] {
    override val commands = Set(
      "disableoutput",
      "enableoutput",
      "kill",
      "update")
  }
}

