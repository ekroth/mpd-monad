package mpd.actor

import scala.concurrent.Future

import mpd.messages._

object ServerPackets {
  case class Raw(s: String)
}

import ServerPackets._
trait ActorComponent {
  def actor: BasicActor

  final def ask[T](x: Any)(implicit tag: scala.reflect.ClassTag[T]) = 
    (actor ask x).mapTo[T]

  final def tell(x: Any) = actor tell x

  trait BasicActor {
    def ask(msg: Any): Future[Any]
    def tell(msg: Any): Unit
  }
}

trait ServerMessagesActor extends ServerMessages {
  self: ActorComponent =>

  override def raw(s: String) = actor ask Raw(s)
  override def required = Set.empty[String]
}
