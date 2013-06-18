package mpd.mock

import scala.concurrent._
import ExecutionContext.Implicits.global

import scalaz._

import mpd.messages._

trait ActorComponentMock extends ActorComponent {
  override def actor = new BasicActorImpl()

  class BasicActorImpl() extends BasicActor {
    import mpd.messages.{ AdminPackets => AP }
    import mpd.messages.{ PlaybackPackets => PP }
    override def ask(msg: Any) = future {
      msg match {

        case AP.Kill() => Success(())

        case PP.Next() => Success(())

        case _ => Success("mock")
      }
    }

    override def tell(msg: Any) = ()
  }
}






