package mpd.commands

trait Satisfied[A] {
  def commands: Set[String]
  def apply(cmds: Set[String]) = {
    commands forall { cmds contains _ }
  }
}
