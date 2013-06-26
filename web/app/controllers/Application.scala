package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import play.api._
import play.api.libs.Comet
import play.api.libs.iteratee._
import play.api.mvc._
import mpd._
import mpd.messages.Result.DefaultT
import mpd.std._
import mpd.messages._
import mpd.messages.SubSystem._

object Application extends Controller {

  import scalaz._
  import Scalaz._

  val srv = new ServerMsgStd 
    with ExecutorComponentStd 
    with MpdComponentSync 
    with ServerDebug
    with PlaybackMsgStd 
    with StatusMsgStd 
  val event = new ServerMsgStd 
    with ExecutorComponentStd 
    with MpdComponentSync 
    with ServerDebug
    with PlaybackMsgStd 
    with StatusMsgStd 

  srv.mpd.connect("192.168.1.2",6600)
  event.mpd.connect("192.168.1.2",6600)
//  event.idle(player) onComplete handleStatus
  def index(cmd: String = "index") = Action {
    Ok(views.html.main(getPlayList))
  }

  def getPlayList = {
      val dsa = "dsa".some
     Song(dsa,dsa,0.some,dsa,dsa,dsa,dsa,dsa,dsa,Seq("dsa").some,dsa,dsa,0.some,0.some) :: 
     Song(dsa,dsa,0.some,dsa,dsa,dsa,dsa,dsa,dsa,Seq("dsa").some,dsa,dsa,0.some,0.some) :: 
     Nil
  }
  
  def issueCmd[T](cmd: => Future[DefaultT[T]]) = { 
    println("woo")
    cmd onComplete { case x => println(x) }
    Ok("ok")
  } 

  def pley = Action {
    issueCmd(srv.play())
  }

  def next = Action {
    issueCmd(srv.next)
  }

  def prev = Action {
    issueCmd(srv.previous)
  }

  def stop = Action {
    issueCmd(srv.stop)
  }

  def pause = Action {
    issueCmd(srv.pause())
  }

  def currentSong = Action {
    Async {
      srv.currentsong.map { 
        _ match {
          //TODO: getOrElse for x attributes
          case \/-(s) => Ok(s.map(x => s"""${x.title.get} - ${x.artist.get}  (${x.time.get})""" ).getOrElse("No song playing."))
          case -\/(e) => Ok(s"Invalid song: $e")
        } 
      }
    }
  }

  def eventFeed = Action {
    val events = Enumerator("kiki", "foo", "bar")
    Ok.stream(clock &> Comet(callback = "parent.cometMessage"))
  }
  
  def blank = Action {
    Ok("")
  } 
  
  lazy val clock: Enumerator[String] = {
    Enumerator.generateM{ 
      for (x <- event.idle()) yield {
        x match { 
          case \/-(v) => ; v.mkString(", ").some 
          case _ => None
        }
      }
    }
  }
  /**
   * Debug!
   */
  def reconnect = Action {
    srv.mpd.disconnect
    srv.mpd.connect("192.168.1.2",6600)
    Ok("Andrée is the webprogrammer")
  }


}
