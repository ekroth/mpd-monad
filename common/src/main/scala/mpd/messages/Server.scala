package mpd.messages

import scala.concurrent.Future

trait ServerMessages {
  def raw(s: String): Future[Any]
  def read(): Future[DefaultT[Vector[String]]] = ???
  def required: Set[String]
}
