package mpd
package messages

import scala.concurrent.ExecutionContext

trait ExecutorComponent {
  implicit def executor: ExecutionContext
}
