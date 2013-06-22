package mpd.messages

import scala.concurrent.Future

trait AdminMessages extends ServerMessages {
  def disableoutput(id: Int): Future[DefaultOK]
  def enableoutput(id: Int): Future[DefaultOK]
  def kill(): Future[PossibleACK]
  def update(path: Option[String]): Unit

  abstract override def required = super.required ++ Set(
    "disableoutput",
    "enableoutput",
    "kill",
    "update")
}
