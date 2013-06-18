package mpd.messages

import scala.concurrent.Future

import mpd.Result._

object AdminPackets {
  case class DisableOutput(id: Int)
  case class EnableOutput(id: Int)
  case class Kill()
  case class Update(path: Option[String])
}

import AdminPackets._
trait AdminMessages extends ServerMessages {
  def disableoutput(id: Int): Future[OKResult]
  def enableoutput(id: Int): Future[OKResult]
  def kill(): Future[PossibleACK]
  def update(path: Option[String]): Unit

  abstract override def supported = super.supported ++ Set(
    "disableoutput",
    "enableoutput",
    "kill",
    "update")
}

trait AdminActorMessages extends AdminMessages {
  self: ActorComponent =>

  override def disableoutput(id: Int) = ask[OKResult](DisableOutput(id))
  override def enableoutput(id: Int) = ask[OKResult](EnableOutput(id))
  override def kill() = ask[PossibleACK](Kill())
  override def update(path: Option[String]) = tell(Update(path))
}

