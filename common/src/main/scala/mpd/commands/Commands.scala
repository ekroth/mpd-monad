package mpd.commands

class Commands {
  def satisfied(cmds: Set[String]) = {
    val req = reqs
    req forall { cmds contains _ }
  }

  def reqs: Set[String] = Set.empty
}
