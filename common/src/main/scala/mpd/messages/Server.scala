package mpd
package messages

import scala.concurrent.Future

import Result._

trait ServerMsg {
  this: ExecutorComponent =>
  def raw(s: String): Future[PossibleError]
  def read(): Future[DefaultT[Vector[String]]]
  def clear(): Future[PossibleError]
  def wread(s: String) = for {
    _ <- clear
    _ <- raw(s)
    x <- read
  } yield x
  def required: Set[String]
}
