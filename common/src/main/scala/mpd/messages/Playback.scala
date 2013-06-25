package mpd
package messages

import scala.concurrent.Future

import Result._

trait PlaybackMsg extends ServerMsg {
  this: ExecutorComponent =>
  /** Plays next song in playlist. */
  def next(): Future[DefaultOK]

  /** Toggle pause / resume playing.
   * @param p Should either be 1 (for paused) or 0 (for playing).
   */
  def pause(p: Option[Boolean]): Future[DefaultOK]

  /** Toggle pause / resume playing. */
  def pause(): Future[DefaultOK] = pause(None)

  /** Begin playing the playlist.
   * @param i The song to begin playing the playlist at, it is optional, the default is -1.
   */
  def play(i: Option[Int]): Future[DefaultOK]

  /** Begin playing the playlist. */
  def play(): Future[DefaultOK] = play(None)

  /** Begin playing playlist.
   * @param i The songid to begin the playlist playing at, it is optional, the default is 0.
   */
  def playid(i: Option[Int]): Future[DefaultOK] = ???

  /** Plays previous song in playlist. */
  def previous(): Future[DefaultOK]

  /* seek
   * seekid
   * setvol
   */
  
  /** To halt playing. */
  def stop(): Future[DefaultOK]
  
  abstract override def required = super.required ++ Set()
}
