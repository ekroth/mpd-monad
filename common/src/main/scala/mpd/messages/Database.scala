package mpd.messages

import scala.concurrent.Future

import mpd.Result._

object DatabasePackets {
  case class URI(str: String)
}

import DatabasePackets._

trait DatabaseMessages extends ServerMessages {
  def listAll(uri: URI): Future[DefaultT[Any]]

  abstract override def required = super.required ++ Set(
    "count",
    "find",
    "findadd",
    "list",
    "listall",
    "listallinfo",
    "lsinfo",
    "search",
    "searchadd",
    "searchaddpl",
    "update",
    "rescan")
}

trait DatabaseActorMessages extends DatabaseMessages {
  self: ActorComponent =>

  override def listAll(uri: URI) = ask[DefaultT[Any]](uri)
}
