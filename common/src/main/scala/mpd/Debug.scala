package mpd

import scalaz._
import Scalaz._

trait DebugOps {
  import BaseFunctions._
  import BaseInstances._

  def bogus(s: String) = MPDF[String] {
    x => MPDBogus(s"I don't care: $s.").left
  }

  def mustflushed() = MPDF[String] {
    case MPDS(false, _) => MPDBogus("Hey no flush!").left
    case x => (x, "Good!").right
  }

  def copycat(s: String) = MPD[String] {
    x => (x copy (flushed = false), s)
  }  
}

final object DebugOps extends DebugOps
