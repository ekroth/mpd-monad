package mpd

import scalaz._

trait Playback {
  /** next */
  def next()(implicit b: Base) = b.writeln("next")
 
  /** pause */
  def pause(p: Option[Boolean])(implicit b: Base) = 
    b.writeln(s"pause ${p.getOrElse(false).toMpd}")

  /** play */
  def play(i: Option[Int])(implicit b: Base) = 
    b.writeln(s"play id ${p.getOrElse(-1)}")

  /** play id */
  def playid(i: Option[Int])(implicit b: Base) = 
    b.writeln(s"playid ${p.getOrElse(-1)}")

  /** previous */
  def previous()(implicit b: Base) = b.writeln("previous")

  /** seek by position in play list to time */
  def seek(pos: Int, time: Int)(implicit b: Base) = 
    b.writeln(s"seek $pos $time")

  /** seek by id to time */
  def seek(id: Int, time: Int)(implicit b: Base) = 
    b.writeln(s"seekid $id $time")

  /** seek by current to time */
  def seekcur(time: Int)(implicit b: Base) = b.writeln(s"seekcur $time")

  /** stop */
  def stop()(implicit b: Base) = b.writeln("stop")
}

trait PlaybackInstances {
  implicit val playBackImplicit = new Playback { }
}

final object PlaybackInstances extends PlaybackInstances
