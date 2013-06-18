package mpd.mock

import scala.concurrent._
import ExecutionContext.Implicits.global

import scalaz._

import mpd.messages._

trait ActorComponentMock extends ActorComponent {
  override def actor = new BasicActorImpl()

  class BasicActorImpl() extends BasicActor {
    override def ask(msg: Any) = future {
      msg match {

        case Kill() => Success(())

        case Next() => Success(())

        case _ => Success("mock")
      }
    }

    override def tell(msg: Any) = ()
  }
}
