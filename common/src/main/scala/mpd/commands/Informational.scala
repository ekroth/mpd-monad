package mpd.commands

import mpd.Result._
import mpd.commands._

trait Informational extends Commands {
  def commands: Result[List[String], ACK]
}
