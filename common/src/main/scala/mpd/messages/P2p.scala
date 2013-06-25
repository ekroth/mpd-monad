package mpd
package messages

import scala.concurrent.Future

trait P2pMsg extends ServerMsg {

  /* subscribe
   * unsubscribe
   * channels
   * readmessages
   * sendmessage
   */
  
  abstract override def required = super.required ++ Set()
}
