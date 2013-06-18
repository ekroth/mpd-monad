package mpd.messages

import scala.concurrent.Future

import mpd.FileSystem._
import mpd.Result._

object DatabasePackets {
  case class ListAll(uri: URI)
}

import DatabasePackets._

trait DatabaseMessages extends ServerMessages {
  def listAll(uri: URI): Future[DefaultT[List[URI]]]

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

  override def listAll(uri: URI) = ask[DefaultT[List[URI]]](ListAll(uri))
}
