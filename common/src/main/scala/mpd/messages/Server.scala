package mpd
package messages

import scala.concurrent.Future

trait ServerMsg {
  this: ExecutorComponent =>

  private[this] def makeCmd(s: String, a1: String): String = s"""$s "$a1""""

  private[this] def makeCmd(s: String, a1: String, as: Seq[String]): String = {
    val args = (a1 +: as) map { x => s""""$x"""" } mkString(" ")
    s"""$s $args"""
  }

  /* raw */

  def raw(s: String): Future[Unit]

  final def raw(s: String, a1: String): Future[Unit] = raw(makeCmd(s, a1))

  final def raw(s: String, a1: String, as: String*): Future[Unit] = raw(makeCmd(s, a1, as))

  /* wread */

  def wread(s: String): Future[Vector[String]] = for {
    _ <- clear
    _ <- raw(s)
    x <- read
  } yield x

  final def wread(s: String, a1: String): Future[Vector[String]] = wread(makeCmd(s, a1))

  final def wread(s: String, a1: String, as: String*): Future[Vector[String]] = wread(makeCmd(s, a1, as))

  /* various */

  def read(): Future[Vector[String]]

  def clear(): Future[Unit]

  def required: Set[String]
}
