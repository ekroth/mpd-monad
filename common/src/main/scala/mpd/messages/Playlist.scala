package mpd.messages

import scala.concurrent.Future

trait PlaylistMsg extends ServerMsg {

  /* add
   * addid
   * clear
   * delete
   * deleteid
   * move
   * moveid
   * playlist -- deprecated
   * playlistfind
   * playlistid
   */

  /** Seq of songs of current playlist */
  def playlistinfo(): Future[DefaultT[Seq[Song]]]

  /* playlistsearch
   * plchanges
   * plchangesposid
   * prio
   * prioid
   * shuffle
   * swap
   * swapid
   */
  
  abstract override def required = super.required ++ Set()
}
