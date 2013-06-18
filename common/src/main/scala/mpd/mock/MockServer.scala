package mpd.mock

import scala.concurrent._

import scalaz._

import mpd.Server._
import mpd.Result._

trait MockServerTypes {
  import ExecutionContext.Implicits.global
  trait ActorComponentMock extends ActorComponent {
    override def actor = new BasicActorImpl()

    class BasicActorImpl() extends BasicActor {
      import mpd.messages._
      override def ask(msg: Any) = future {
	msg match {
	  
	  case Admin.Kill() => Success(())

	  case Playback.Next() => Success(())

	  case _ => Success("mock")
	}
      }

      override def tell(msg: Any) = ()
    }
  }
}
