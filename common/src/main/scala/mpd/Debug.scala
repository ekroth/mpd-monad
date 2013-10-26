package mpd

import scalaz._
import Scalaz._

trait Debug extends Base {
  import BaseInstances._

  def bogus(s: String) = MPDF[String] {
    x => MPDBogus(s"I don't care: $s.").left
  }

  def copycat(s: String) = MPD[String] {
    x => (x copy (flushed = false), s)
  }  

  override def write(cmd: String) = for {
    r <- super.write(cmd)
    _ = println(s"MPD: write -> $cmd")
  } yield r

  override def read(): MPD[Vector[String]] = for {
    r <- super.read()
    _ = println(s"MPD: read <- $r")
  } yield r

  override def flush() = for {
    r <- super.flush()
    _ = println("MPD: flush")
  } yield r
}

trait DebugInstances extends BaseInstances {
  override implicit val baseImplicit = new Debug { }
}

final object DebugInstances extends DebugInstances
