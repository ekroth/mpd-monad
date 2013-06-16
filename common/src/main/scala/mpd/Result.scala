package mpd

import scalaz.Validation
import scala.concurrent.Future

object Result extends ResultFunctions

trait ResultFunctions {
  type Result[A, B] = Future[Validation[A, B]]
  sealed trait Failed
  case class OK()
  case class ACK() extends Failed
  case class Timeout() extends Failed
  type OKResult = Result[Failed, OK]
  type PossibleACK = Result[Failed, Unit]
  type PossibleOK = Result[Unit, OK]
}
