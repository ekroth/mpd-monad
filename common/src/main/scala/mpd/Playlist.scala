package mpd

import scalaz._

trait Playlist {
  /* add
   * addid
   * clear
   * delete
   * deleteid
   * move
   * moveid
   * playlist -- deprecated
   * playlistfind
   * playlistid
   */

  /** playlist info */
  def playlistinfo()(implicit b: Base): MPD[Seq[Map[String, Seq[String]]]] = {
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
      val vpairs = util.MpdParse.valuePairs(s)
      for (p <- grouper(vpairs, vpairs.headOption)) yield p.toMap
    }
  }

  /* playlistsearch
   * plchanges
   * plchangesposid
   * prio
   * prioid
   * shuffle
   * swap
   * swapid
   */
}

trait PlaylistInstances {
  implicit val playlistImplicit = new Playlist { }
}

final object PlaylistInstances extends PlaylistInstances
