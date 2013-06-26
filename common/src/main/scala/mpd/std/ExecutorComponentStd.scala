package mpd
package std

import scala.concurrent.ExecutionContext

import mpd.messages.ExecutorComponent

trait ExecutorComponentStd extends ExecutorComponent {
  override val executor = ExecutionContext.Implicits.global
}
