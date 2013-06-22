package mpd.actor

import mpd.messages._

object AdminPackets {
  case class DisableOutput(id: Int)
  case class EnableOutput(id: Int)
  case class Kill()
  case class Update(path: Option[String])
}

import AdminPackets._

trait AdminMessagesActor extends AdminMessages {
  self: ActorComponent =>

  override def disableoutput(id: Int) = ask[DefaultOK](DisableOutput(id))
  override def enableoutput(id: Int) = ask[DefaultOK](EnableOutput(id))
  override def kill() = ask[PossibleACK](Kill())
  override def update(path: Option[String]) = tell(Update(path))
}
