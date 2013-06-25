package mpd
package messages

import scala.concurrent.Future

trait PlaybackOptsMsg extends ServerMsg {
  this: ExecutorComponent =>
  /* consume
   * crossfade
   * mixrampdb
   * mixrampdelay
   * random
   * repeat
   * setvol
   * single
   * replay_gain_mode
   * replay_gain_status
   */
  
  abstract override def required = super.required ++ Set()
}
