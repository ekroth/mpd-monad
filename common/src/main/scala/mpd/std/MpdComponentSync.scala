package mpd
package std

import scalaz._
import Scalaz._

import mpd.{ MPDConnection => MPDC }
import messages.Result._

trait MpdComponentSync extends MpdComponent {
  override val mpd = synchronized {
    new BasicMpdImpl()
  }

  final class BasicMpdImpl extends BasicMpd {
    private[this] var mpd = none[MPDC]

    override def con = mpd.get
    override def connect(addr: String, port: Int) = {
      MPDC.connect(addr, port) match {
	case \/-(x) => { 
	  mpd = x.some
	  ().right
	}
	case -\/(x) => x.left
      }
    }

    override def disconnect() {
      mpd foreach { _.disconnect }
    }
  }
}
