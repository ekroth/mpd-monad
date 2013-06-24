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
   * playlistinfo
   * playlistsearch
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
