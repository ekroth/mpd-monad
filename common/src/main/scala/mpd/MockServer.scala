package mpd

import scala.concurrent._

import scalaz._

import mpd.messages.Admin._
import mpd.Server._
import mpd.Result._

object Mock extends MockTypes

trait MockTypes {
  import ExecutionContext.Implicits.global
  trait ActorComponentMock extends ActorComponent {
    override def actor = new BasicActorImpl

    class BasicActorImpl extends BasicActor {
      override def ask(msg: Any) = future {
	msg match {
	  case Kill() => Success(())
	}
      }

      override def tell(msg: Any) = ()
    }
  }

  trait MockServer extends ServerActorMessages {
    self: ActorComponent =>
    override def raw(s: String) = future { None }
    override def supported = Set("kill")
  }
}

