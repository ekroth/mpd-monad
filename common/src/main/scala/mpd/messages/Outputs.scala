package mpd
package messages

import scala.concurrent.Future

trait OutputsMsg extends ServerMsg {
  this: ExecutorComponent =>
  /* disableoutput
   * enableooutput
   * outputs
   */
  
  abstract override def required = super.required ++ Set()
}
