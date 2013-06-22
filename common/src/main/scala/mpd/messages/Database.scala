package mpd.messages

import scala.concurrent.Future

import mpd.FileSystem._

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
