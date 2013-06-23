package controllers

import play.api._
import play.api.mvc._
import mpd._
import mpd.std._
import mpd.messages._

object Application extends Controller {

  import scalaz._
  import Scalaz._

  val srv = new ServerMessagesStd with MpdComponentSync with ServerMessagesDebug with PlaybackMessagesStd
  
  val connection = srv.mpd.connect("192.168.1.2",6600)

  def index(cmd: String = "index") = Action {
    Ok(views.html.index(cmd))
  }


  
  def issueCmd[T](cmd: => T) = { 
    cmd
    Redirect("/")
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

}
