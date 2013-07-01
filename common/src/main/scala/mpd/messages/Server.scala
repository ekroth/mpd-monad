package mpd
package messages

import scala.concurrent.Future

trait ServerMsg {
  this: ExecutorComponent =>

  def raw(s: String): Future[Unit]

  final def raw(s: String, a1: String): Future[Unit] = raw(s"""$s "$a1"""")

  final def raw(s: String, a1: String, as: String*): Future[Unit] = {
    val args = (a1 +: as) map { x => s""""$x"""" } mkString(" ")
    raw(s"""$s $args""")
  }

  def read(): Future[Vector[String]]

  def clear(): Future[Unit]

  def wread(s: String) = for {
    _ <- clear
    _ <- raw(s)
    x <- read
  } yield x

  def required: Set[String]
}
