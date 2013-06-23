package mpd.messages

import scala.concurrent.Future

trait ResultTypes {
  import scalaz._
  import Scalaz._

  case class OK()

  sealed trait Error
  case class ACK(err: String, msg: String) extends Error
  case class Disconnected() extends Error
  case class Unknown(s: String) extends Error

  type DefaultOK = Error \/ OK
  type DefaultAny = Error \/ Any
  type DefaultT[T] = Error \/ T
  type PossibleError = Error \/ Unit
  type PossibleACK = ACK \/ Unit
  type PossibleOK = Unit \/ OK
  type PossibleT[T] = Unit \/ T
  type PossibleU[T] = T \/ Unit

  implicit class BooleanROps(val v: Boolean) {
    def toInt = if (v) 1 else 0
    def toMpd = toInt.toString
  }
}
