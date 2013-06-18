package mpd.mock

import scala.concurrent._
import ExecutionContext.Implicits.global

import scalaz._

import mpd.messages._

trait ActorComponentMock extends ActorComponent {
  override def actor = new BasicActorImpl()

  import mpd.FileSystem._

  val fs = Map(
    "/" -> ("Music/" :: Nil),
    "/Music/" -> ("Analog/" :: "Electronic/" :: Nil),
    "/Music/Analog/" -> ("Pink Fluyd/" :: "Tuul/" :: Nil),
    "/Music/Analog/Pink Fluyd/" -> ("Wall of bricks.mp3" :: "Money money money.mp3" :: Nil),
    "/Music/Analog/Tuul/" -> ("Sober by lättöl.ogg" :: "Flowers and spirals.ogg" :: Nil),
    "/Music/Electronic/" -> ("Aphex twons/" :: Nil),
    "/Music/Electronic/Aphex twons/" -> ("Windowcleaner.flac" :: Nil))
									    

  class BasicActorImpl() extends BasicActor {
    import mpd.messages.{ AdminPackets => AP }
    import mpd.messages.{ PlaybackPackets => PP }
    import mpd.messages.{ DatabasePackets => DP }
    import Scalaz._
    override def ask(msg: Any) = future {
      msg match {

        case AP.Kill() => Success(())

        case PP.Next() => Success(())

	case DP.ListAll(uri) => fs.get(uri).getOrElse(Nil).right
	

        case _ => Success("mock")
      }
    }

    override def tell(msg: Any) = ()
  }
}






