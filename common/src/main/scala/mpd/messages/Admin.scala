package mpd.messages

import scala.concurrent.Future

import mpd.Server
import mpd.Result._
import mpd.Server._

object Admin extends AdminTypes

trait AdminTypes {
  case class DisableOutput(id: Int)
  case class EnableOutput(id: Int)
  case class Kill()
  case class Update(path: Option[String])

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

    override def disableoutput(id: Int) = (actor ask DisableOutput(id)).mapTo[OKResult]
    override def enableoutput(id: Int) = (actor ask EnableOutput(id)).mapTo[OKResult]
    override def kill() = (actor ask Kill()).mapTo[PossibleACK]
    override def update(path: Option[String]) = actor tell path
  }
}
