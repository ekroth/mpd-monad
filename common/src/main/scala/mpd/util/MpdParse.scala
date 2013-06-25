package mpd.util

object MpdParse {
  import mpd.messages._

  val songr = """([-\w]*): (.*)""".r

  def valuePairs(v: Traversable[String]) = {
    val matches = songr.findAllMatchIn(v.mkString("\n"))

    matches.foldLeft(Seq.empty[(String, Seq[String])]) { (b, a) =>
      val key = a.group(1).toLowerCase
      val old = for (
        h <- b.lastOption;

        if h._1 == key
      ) yield h._2

      (if (old.isDefined) b.init else b) :+ 
	(key -> (old.getOrElse(Seq.empty) :+ a.group(2)))
    }
  }

  def mapValues(v: Traversable[String]) = valuePairs(v).toMap
  def parseSong(s: Map[String, Seq[String]]): DefaultT[Option[Song]] = {
    def ohead[A, B](m: Map[A, _ <: Traversable[B]], key: A): Option[B] = 
      for (x <- m.get(key)) yield x.head

    import scalaz._
    import Scalaz._
    try {
      (if (s.isEmpty) None else
        Some(Song(
          ohead(s, "file"),
          ohead(s, "last-modified"),
          for (x <- ohead(s, "time")) yield x.toInt,
          ohead(s, "title"),
          ohead(s, "artist"),
          ohead(s, "album"),
          ohead(s, "albumartist"),
          ohead(s, "genre"),
	  ohead(s, "date"),
          s.get("composer"),
          ohead(s, "disc"),
          ohead(s, "track"),
          for (x <- ohead(s, "pos")) yield x.toInt,
          for (x <- ohead(s, "id")) yield x.toInt))).right
    } catch {
      case e: Throwable => Unknown(s"(parseSong), error parsing $e, with map $s").left
    }
  }
}
