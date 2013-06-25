package mpd
package messages

import scala.concurrent.Future

trait ConnectionMsg extends ServerMsg {
  this: ExecutorComponent =>
  /* close
   * kill
   * password
   * ping
   */
  
  abstract override def required = super.required ++ Set()
}
