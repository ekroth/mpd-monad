package mpd

import scalaz._

trait Playlist {
   def playlistinfo()(implicit b: Base) = {
    @annotation.tailrec def grouper[T <: Tuple2[_, _]](xs: Seq[T],
					    delim: Option[T],
					    ys: Seq[Traversable[T]] = Seq.empty): Seq[Traversable[T]] = {
      if (xs.isEmpty || delim.isEmpty) ys
      else {
        val take = (xs.tail takeWhile { _._1 != delim.get._1 }) :+ xs.head
        grouper(xs.drop(take.length), delim, ys :+ take)
      }
    }

    b.wread("playlistinfo") map { s =>
      val vpairs = MpdParse.valuePairs(s)
      val pairs = grouper(vpairs, vpairs.headOption)
      for (p <- pairs) yield MpdParse.parseSong(p.toMap)
    }
  }

}

trait PlaylistInstances {
  implicit val playlistImplicit = new Playlist { }
}

final object PlaylistInstances extends PlaylistInstances
