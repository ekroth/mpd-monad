package mpd
package std

import scalaz._
import Scalaz._

trait MpdComponentSync extends MpdComponent {
  override val mpd = synchronized {
    new BasicMpdImpl()
  }

  final class BasicMpdImpl extends BasicMpd {
    private[this] var mpd = none[MPDC]

    override def con = mpd.get
    override def connect(addr: String, port: Int) = 
      mpd = (new MPDCStd(addr, port) with MPDCSync).some

    override def disconnect() {
      mpd foreach { _.disconnect }
    }
  }
}
