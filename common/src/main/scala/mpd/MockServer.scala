package mpd

import scala.concurrent._

import scalaz._

import mpd.messages.Admin._
import mpd.messages.AdminComponent
import mpd.Result._

object Mock extends MockTypes

trait MockTypes {
  import ExecutionContext.Implicits.global
  trait MockServer extends ServerComponent {
    override def raw(s: String) = future { None }
    override def supported = Set.empty
  }

  trait MockAdmin extends AdminComponent {
    def disableoutput(id: Int): OKResult = future { Success(OK()) }
    def enableoutput(id: Int): OKResult = future { Success(OK()) }
    def kill(): PossibleACK = future { Success(()) }
    def update(path: Option[String]): Unit = ???
  }
}

