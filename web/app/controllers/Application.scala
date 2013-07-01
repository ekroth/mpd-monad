package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

import play.api._
import play.api.libs.Comet
import play.api.libs.iteratee._
import play.api.mvc._

import mpd._
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

  def index = Action {
    try {
      Async { 
        srv.currentSong map { v =>
          val opt = v map { x =>
            Ok(views.html.main(getPlayList,s"""${x.title.get} - ${x.artist.get}  (${x.time.get})"""))
          }
          opt.getOrElse(Ok(views.html.main(getPlayList, "No song playing")))
        } 
      }
    } catch {
      case x: Throwable => Redirect("/connect")
    }
  }

  def getPlayList = {
    val dsa = "dsa".some
    Song(dsa,dsa,0.some,dsa,dsa,dsa,dsa,dsa,dsa,Seq("dsa").some,dsa,dsa,0.some,0.some) :: 
    Song(dsa,dsa,0.some,dsa,dsa,dsa,dsa,dsa,dsa,Seq("dsa").some,dsa,dsa,0.some,0.some) :: 
    Nil
  }
  
  def issueCmd[T](cmd: => Future[T]) = { 
    cmd onComplete { case x => println(x) }
    Ok("ok").withHeaders("Cache-Control" -> "no-store, no-cache")
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
      srv.currentSong map { v =>
	      val opt = v map { x =>
          Ok(s"""${x.title.get} - ${x.artist.get}  (${x.time.get})""").withHeaders("Cache-Control" -> "no-store, no-cache")
	      }
	      opt.getOrElse(Ok("No song playing")).withHeaders("Cache-Control" -> "no-store, no-cache")
      }
    }
  }

  def eventFeed = Action {
    Ok.stream(clock &> Comet(callback = "parent.cometMessage"))
  }
  
  def blank = Action {
    Ok("")
  } 
  
  lazy val clock: Enumerator[String] = {
    Enumerator.generateM { 
      event.idle() map { _.mkString(", ").some } 
    }
  }

  def connect = Action {
    try {
    srv.mpd.disconnect
    srv.mpd.connect("192.168.1.2",6700)
    event.mpd.disconnect
    event.mpd.connect("192.168.1.2",6700)
    Ok("Andrée is the webprogrammer")
    } catch { 
      case x: Throwable => Ok(x.getMessage)
    }
  }
 
}
