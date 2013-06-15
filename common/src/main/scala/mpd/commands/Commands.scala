package mpd.commands

trait Commands {
  def allSupported(cmds: Set[String]) = reqs forall { cmds contains _ }
  def reqs: Set[String] = Set.empty
}
