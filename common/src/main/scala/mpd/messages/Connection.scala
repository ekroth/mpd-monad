package mpd.messages

import scala.concurrent.Future

trait ConnectionMsg extends ServerMsg {

  /* close
   * kill
   * password
   * ping
   */
  
  abstract override def required = super.required ++ Set()
}
