package mpd.messages

import mpd.Server

import mpd.Result._
import mpd.Server._

object Info extends InfoTypes

trait InfoTypes {
  trait InfoMessages extends ServerMessages
  trait InfoActorMessages extends InfoMessages
}
