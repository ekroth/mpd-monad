package mpd

import scala.concurrent.Future

object Server extends ServerTypes

trait ServerTypes {
  case class Raw(s: String)
}

trait AskComponent {
  def ask(msg: Any): Future[Any]
  def tell(msg: Any): Unit
}

trait ServerComponent {
  def raw(s: String): Future[Any]
  def supported: Set[String]
}

trait ServerImpl extends ServerComponent {
  self: AskComponent =>
  import Server._

  override def raw(s: String) = {
    self ask Raw(s)
  }

  override val supported = Set.empty[String]
}
