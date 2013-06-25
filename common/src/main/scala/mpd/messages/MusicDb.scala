package mpd
package messages

import scala.concurrent.Future

trait MusicDbMsg extends ServerMsg {

  /* count
   * find
   * findadd
   * list
   * listall
   * listallinfo
   * lsinfo
   * search
   * searchadd
   * searchaddpl
   * update
   * rescan
   */
  
  abstract override def required = super.required ++ Set()
}
