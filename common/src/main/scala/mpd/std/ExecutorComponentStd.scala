package mpd
package std

import scala.concurrent.ExecutionContext

import messages.ExecutorComponent

trait ExecutorComponentStd extends ExecutorComponent {
  override def executor = ExecutionContext.Implicits.global
}
