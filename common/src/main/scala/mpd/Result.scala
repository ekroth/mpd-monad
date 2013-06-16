package mpd

import scalaz.{ Validation => Val }
import scala.concurrent.Future

object Result extends ResultTypes

trait ResultTypes {
  sealed trait Failed
  case class OK()
  case class ACK() extends Failed
  case class Timeout() extends Failed
  type OKResult = Val[Failed, OK]
  type PossibleACK = Val[Failed, Unit]
  type PossibleOK = Val[Unit, OK]
}
