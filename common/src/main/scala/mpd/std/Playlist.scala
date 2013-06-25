package mpd.std

import scala.concurrent._
import ExecutionContext.Implicits.global

import mpd.{ MPDConnection => MPDC }
import mpd.messages._
import mpd.util._

trait PlaylistMsgStd extends PlaylistMsg {
  import scalaz._
  import Scalaz._

  override def playlistinfo() = {
    def grouper[T <: Tuple2[_, _]](xs: Seq[T], 
				   delim: Option[T], 
				   ys: Seq[Traversable[T]] = Seq.empty): Seq[Traversable[T]] = {
      if (xs.isEmpty || delim.isEmpty) ys
      else {
        val take = (xs.tail takeWhile { _._1 != delim.get._1 }) :+ xs.head
        grouper(xs.drop(take.length), delim, ys :+ take)
      }
    }

    wread("playlistinfo") map { x =>
      val res = for { v <- x } yield {
        val vpairs = MpdParse.valuePairs(v)
        val pairs = grouper(vpairs, vpairs.headOption)
        val songs = for (p <- pairs) yield MpdParse.parseSong(p.toMap)
        val err = songs find { _.isLeft }

        if (err.isDefined) {
          val -\/(e) = err.get
          e.left
        } else (songs map { s: DefaultT[Option[Song]] =>
          val \/-(x) = s
          x.get
        }).toSeq.right
      }

      res match {
        case -\/(x) => x.left
        case \/-(x) => x
      }
    }
  }

}
