package mpd
package std

import scala.concurrent.ExecutionContext.Implicits.global
import scala.annotation.tailrec

import mpd.messages.{ PlaylistMsg, ExecutorComponent }
import mpd.util.MpdParse

trait PlaylistMsgStd extends PlaylistMsg {
  this: ExecutorComponent =>
  import scalaz._
  import Scalaz._

  override def playlistinfo() = {
    @tailrec def grouper[T <: Tuple2[_, _]](xs: Seq[T],
      delim: Option[T],
      ys: Seq[Traversable[T]] = Seq.empty): Seq[Traversable[T]] = {
      if (xs.isEmpty || delim.isEmpty) ys
      else {
        val take = (xs.tail takeWhile { _._1 != delim.get._1 }) :+ xs.head
        grouper(xs.drop(take.length), delim, ys :+ take)
      }
    }

    wread("playlistinfo") map { s =>
      val vpairs = MpdParse.valuePairs(s)
      val pairs = grouper(vpairs, vpairs.headOption)
      for (p <- pairs) yield MpdParse.parseSong(p.toMap)
    }
  }
}
