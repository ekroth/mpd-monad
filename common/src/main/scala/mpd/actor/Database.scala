package mpd.actor

import mpd.messages._

object DatabasePackets {
  case class ListAll(uri: String)
}

import DatabasePackets._
trait DatabaseMessagesActor extends DatabaseMessages {
  self: ActorComponent =>

  override def listAll(uri: String) = ask[DefaultT[List[String]]](ListAll(uri))
}
