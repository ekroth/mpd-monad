package mpd

import scalaz._
import Scalaz._

trait DebugFunctions extends BaseFunctions {
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

  abstract override def write(cmd: String): MPD[Unit] = for {
    r <- super.write(cmd)
    _ = println(s"MPD: write -> $cmd")
  } yield r

  abstract override def read(): MPD[Vector[String]] = for {
    r <- super.read()
    _ = println(s"MPD: read <- $r")
  } yield r

  abstract override def flush(): MPD[Unit] = for {
    r <- super.flush()
    _ = println("MPD: flush")
  } yield r
}

final object DebugFunctions extends DebugFunctions
