package mpd

import scala.concurrent.Future

trait Server {
  def isConnected: Boolean
  def command(s: String): Future[String]
  def batch(xs: Traversable[String]): Future[Seq[String]]
  def supported: Future[Set[String]]
}
