package mpd
package messages

import scala.concurrent.Future

trait StickersMsg extends ServerMsg {

  /* sticker <>
   * get
   * set
   * delete
   * list
   * find
   */
  
  abstract override def required = super.required ++ Set()
}
