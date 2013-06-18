package mpd

import scala.concurrent.Future

object Result extends ResultTypes

trait ResultTypes {
  import scalaz._
  import Scalaz._

  case class OK()

  trait Error
  case class ACK() extends Error
  case class Timeout() extends Error

  type DefaultOK = Error \/ OK
  type DefaultAny = Error \/ Any
  type DefaultT[T] = Error \/ T
  type PossibleACK = ACK \/ Unit
  type PossibleOK = Unit \/ OK
  type PossibleT[T] = Unit \/ T
  type PossibleU[T] = T \/ Unit
}
