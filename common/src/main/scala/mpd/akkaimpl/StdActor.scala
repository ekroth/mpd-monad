package mpd.akkaimpl

import akka.actor._

trait ActorTypes {
  class StdActor extends Actor {

    def receive = {

      case _      => None
    }
  }

  trait MessageHandler {
    def handle: PartialFunction[Any, Any]
  }

  trait AdminMessageHandler {
    //import 
  }
}
