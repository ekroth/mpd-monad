package mpd
package std

import scala.concurrent.ExecutionContext.Implicits.global
import scala.annotation.tailrec

import mpd.messages.{ MusicDbMsg, ExecutorComponent }
import mpd.util.MpdParse

trait MusicDbMsgStd extends MusicDbMsg {
  this: ExecutorComponent =>
  import scalaz._
  import Scalaz._

  
}
