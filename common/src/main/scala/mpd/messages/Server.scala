package mpd.messages

import scala.concurrent.Future

trait ServerMessages {
  def raw(s: String): Future[Any]
  def read(): Future[Any] = ???
  def required: Set[String]
}
