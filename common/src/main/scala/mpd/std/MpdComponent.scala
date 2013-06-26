package mpd
package std

import messages.Result.PossibleError

trait MpdComponent {
  def mpd: BasicMpd

  trait BasicMpd {
    def con: MPDC
    def connect(addr: String, port: Int): PossibleError
    def disconnect(): Unit
  }
}
