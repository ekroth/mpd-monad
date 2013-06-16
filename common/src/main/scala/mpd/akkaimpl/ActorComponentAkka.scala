package mpd.akkaimpl

import scala.concurrent.Future

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import mpd.Server._

object ActorComponentAkka extends AkkaTypes

trait AkkaTypes {
  trait ActorComponentAkka extends ActorComponent {
    override def actor = new BasicActorImpl

    class BasicActorImpl extends BasicActor {
      //val actor: ActorRef = ???
      //implicit val timeout: Timeout = ???

      override def ask(msg: Any) = ask(actor, msg)
      override def tell(msg: Any) = tell(actor, msg)
    }
  }
}










