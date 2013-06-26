package mpd
package messages

import scala.concurrent.Future

trait ServerMsg {
  this: ExecutorComponent =>
  def raw(s: String): Future[Unit]
  def read(): Future[Vector[String]]
  def clear(): Future[Unit]
  def wread(s: String) = for {
    _ <- clear
    _ <- raw(s)
    x <- read
  } yield x

  def required: Set[String]
}
