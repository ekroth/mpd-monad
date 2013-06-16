package mpd.messages

import mpd.Server

import mpd.Result._
import mpd.ServerComponent

object Info extends InfoTypes

trait InfoTypes {
}

trait InfoComponent extends ServerComponent {
  abstract override def supported = super.supported ++ Set()
}
