package mpd.messages

import scala.concurrent.Future

trait OutputsMsg extends ServerMsg {

  /* disableoutput
   * enableooutput
   * outputs
   */
  
  abstract override def required = super.required ++ Set()
}
