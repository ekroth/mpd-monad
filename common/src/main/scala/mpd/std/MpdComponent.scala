package mpd
package std

trait MpdComponent {
  def mpd: BasicMpd

  trait BasicMpd {
    def con: MPDC
    def connect(addr: String, port: Int): Unit
    def disconnect(): Unit
  }
}
