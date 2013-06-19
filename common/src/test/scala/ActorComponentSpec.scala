import org.scalatest.FunSpec

import org.scalatest.BeforeAndAfterEach

import scala.concurrent._
import scala.concurrent.duration._
import scalaz._
import ExecutionContext.Implicits.global

import mpd.mock._
import mpd.messages._
import mpd.FileSystem._

class ActorComponentSpec extends FunSpec with BeforeAndAfterEach {
  trait ActorComponentMockTest extends ActorComponentMock {
    val fs = Map(
      "/" -> ("Music/" :: Nil),
      "/Music/" -> ("Analog/" :: "Electronic/" :: Nil),
      "/Music/Analog/" -> ("Pink Fluyd/" :: "Tuul/" :: Nil),
      "/Music/Analog/Pink Fluyd/" -> ("Wall of bricks.mp3" :: "Money money money.mp3" :: Nil),
      "/Music/Analog/Tuul/" -> ("Sober by lättöl.ogg" :: "Flowers and spirals.ogg" :: Nil),
      "/Music/Electronic/" -> ("Aphex twons/" :: Nil),
      "/Music/Electronic/Aphex twons/" -> ("Windowcleaner.flac" :: Nil))
  }

  val srv: ServerMessages with DatabaseMessages with ActorComponentMockTest =
    new ServerActorMessages with DatabaseActorMessages with ActorComponentMockTest

  override def beforeEach() {
    srv.reset
    super.beforeEach()
  }

  describe("An ActorComponent(MockTest)") {
    describe("when listing database") {
      it("should be able to list db") {
        val out = for {
	  (uri, ents) <- srv.fs
        } { 
	  Await.result(srv.listAll(uri), 1.seconds) match {
	    case \/-(x) => assert(x === ents)
	    case x => fail(s"$x is unknown")
	  }
	}
      }
    }
  }
}
