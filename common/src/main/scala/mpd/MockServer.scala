package mpd.mock

import mpd.Server
import mpd.commands.Commands

import scala.concurrent._

class MockServer extends Server with Commands {
  import ExecutionContext.Implicits.global
  override val isConnected = true
  override def command(s: String) = s match {
    case "commands" => future { supported.value.get.get.mkString("", "\n", "OK\n") }
    case _ => ???
  }
  override def batch(xs: Traversable[String]) = ???
  override val supported = future { Set("commands") }
}










