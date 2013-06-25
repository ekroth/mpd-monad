package mpd
package messages

import scala.concurrent.Future

trait ReflectionMsg extends ServerMsg {

  /* config
   * commands
   * notcommands
   * tagtypes
   * urlhandlers
   * decoders
   */
  
  abstract override def required = super.required ++ Set()
}
