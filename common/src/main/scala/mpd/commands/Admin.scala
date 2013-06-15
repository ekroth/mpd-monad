package mpd.commands

import mpd.Result._
import mpd.commands._

trait Admin extends Commands {
  def disableoutput(id: Int): OKResult
  def enableoutput(id: Int): OKResult
  def kill(): PossibleACK
  def update(path: Option[String]): Unit
}

