package mpd

trait Status {
  import scalaz._
  import Scalaz._

  /** clear error */
  def clearError()(implicit b: Base) = b.writeln("clearerror")

  /** current song */
  def currentSong()(implicit b: Base) = b.wread("currentsong") map { x =>
    val song = util.MpdParse.mapValues(x)
    if (song.nonEmpty) song.some
    else none
  }

  /** idle for new event */
  def idle(xs: Set[SubSystem])(implicit b: Base) = b.wread(s"""idle ${xs.mkString(" ")}""") map { x =>
    (x flatMap { l =>
      val reg = "changed: (.*)".r
      l match {
        case reg(s) => SubSystem.withName(s).some
        case _ => None
      }
    }).toSet
  } 
  
  /** status */
  def status()(implicit b: Base) = b.wread("status") map { util.MpdParse.mapValues(_) }
}

trait StatusInstances {
  implicit val statusImplicit = new Status { }
}

final object StatusInstances extends StatusInstances
