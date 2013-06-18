package mpd

import scala.concurrent.Future

object Server extends ServerTypes

trait ServerTypes {
  case class Raw(s: String)

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

  trait ServerMessages {
    def raw(s: String): Future[Any]
    def supported: Set[String]
  }

  trait ServerActorMessages extends ServerMessages {
    self: ActorComponent =>

    override def raw(s: String) = actor ask Raw(s)
    override def supported = Set.empty[String]
  }
}
