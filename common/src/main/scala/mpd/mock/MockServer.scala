package mpd.mock

import scala.concurrent._
import ExecutionContext.Implicits.global

import scalaz._

import mpd.messages._
import mpd.actor._

trait ActorComponentMockStd extends ActorComponentMock {
  val fs = Map(
    "/" -> ("Music/" :: Nil),
    "/Music/" -> ("Analog/" :: "Electronic/" :: Nil),
    "/Music/Analog/" -> ("Pink Fluyd/" :: "Tuul/" :: Nil),
    "/Music/Analog/Pink Fluyd/" -> ("Wall of bricks.mp3" :: "Money money money.mp3" :: Nil),
    "/Music/Analog/Tuul/" -> ("Sober by lättöl.ogg" :: "Flowers and spirals.ogg" :: Nil),
    "/Music/Electronic/" -> ("Aphex twons/" :: Nil),
    "/Music/Electronic/Aphex twons/" -> ("Windowcleaner.flac" :: Nil))
}

trait ActorComponentMock extends ActorComponent {
  import mpd.FileSystem._

  override def actor = new BasicActorImpl()
  val fs: Map[String, List[String]]

  def reset() = ()
									    
  class BasicActorImpl() extends BasicActor {
    import mpd.actor.{ AdminPackets => AP }
    import mpd.actor.{ PlaybackPackets => PP }
    import mpd.actor.{ DatabasePackets => DP }
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






