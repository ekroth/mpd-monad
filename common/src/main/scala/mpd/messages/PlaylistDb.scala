package mpd
package messages

import scala.concurrent.Future

trait PlaylistDbMsg extends ServerMsg {
  this: ExecutorComponent =>
  /* listplaylist
   * listplaylistinfo
   * listplaylists
   * load
   * playlistadd
   * playlistclear
   * playlistdelete
   * playlistmove
   * rename
   * rm
   * save
   */
  
  abstract override def required = super.required ++ Set()
}
