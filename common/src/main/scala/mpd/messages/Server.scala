package mpd.messages

import scala.concurrent._
import ExecutionContext.Implicits.global

trait ServerMsg {
  def raw(s: String): Future[PossibleError]
  def read(): Future[DefaultT[Vector[String]]]
  def wread(s: String) = for {
    _ <- raw(s)
    x <- read
  } yield x
  def required: Set[String]
}
