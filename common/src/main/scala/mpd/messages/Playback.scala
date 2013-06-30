package mpd
package messages

import scala.concurrent.Future

trait PlaybackMsg extends ServerMsg {
  this: ExecutorComponent =>

  /** Plays next song in playlist. */
  def next(): Future[Unit]

  /** Toggle pause / resume playing.
   * @param p Should either be 1 (for paused) or 0 (for playing).
   */
  def pause(p: Option[Boolean]): Future[Unit]

  /** Toggle pause / resume playing. */
  final def pause(): Future[Unit] = pause(None)

  /** Begin playing the playlist.
   * @param i The song to begin playing the playlist at, it is optional, the default is -1.
   */
  def play(i: Option[Int]): Future[Unit]

  /** Begin playing the playlist. */
  final def play(): Future[Unit] = play(None)

  /** Begin playing playlist.
   * @param i The songid to begin the playlist playing at, it is optional, the default is 0.
   */
  def playid(i: Option[Int]): Future[Unit]

  /** Begin playing the playlist. */
  def playid(): Future[Unit] = playid(None)

  /** Plays previous song in playlist. */
  def previous(): Future[Unit]

  def seek(pos: Int, time: Int): Future[Unit]
  
  def seekid(id: Int, time: Int): Future[Unit]
  
  def seekcur(time: Int): Future[Unit]
  
  /** To halt playing. */
  def stop(): Future[Unit]
  
  abstract override def required = super.required ++ Set()
}
