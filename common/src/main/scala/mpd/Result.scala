package mpd.commands

import scalaz.Validation
import scala.concurrent.Promise

object Result extends Result

trait Result {
  type Result[A, B] = Promise[Validation[A, B]]
  case class OK()
  case class ACK()
  type OKResult = Result[OK, ACK]
  type PossibleACK = Result[Nothing, ACK]
  type PossibleOK = Result[OK, Nothing]
}
