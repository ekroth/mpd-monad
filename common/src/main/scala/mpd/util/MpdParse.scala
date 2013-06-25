package mpd
package util

object MpdParse {
  val songr = """([-\w]*): (.*)""".r

  def valuePairs(v: Traversable[String]) = {
    val matches = songr.findAllMatchIn(v.mkString("\n"))

    matches.foldLeft(Seq.empty[(String, Seq[String])]) { (b, a) =>
      val key = a.group(1).toLowerCase
      val old = for {
        h <- b.lastOption
        if h._1 == key
      } yield h._2

      (if (old.isDefined) b.init else b) :+ 
	(key -> (old.getOrElse(Seq.empty) :+ a.group(2)))
    }
  }

  def mapValues(v: Traversable[String]) = valuePairs(v).toMap
  def parseSong(s: Map[String, Seq[String]]): Song = {
    def ohead[A, B](m: Map[A, _ <: Traversable[B]], key: A): Option[B] = 
      m.get(key) map { _.head }

    import scalaz._
    import Scalaz._
    Song(ohead(s, "file"),
         ohead(s, "last-modified"),
         ohead(s, "time") map { _.toInt },
         ohead(s, "title"),
         ohead(s, "artist"),
         ohead(s, "album"),
         ohead(s, "albumartist"),
         ohead(s, "genre"),
	 ohead(s, "date"),
         s.get("composer"),
         ohead(s, "disc"),
         ohead(s, "track"),
         ohead(s, "pos") map { _.toInt },
         ohead(s, "id") map { _.toInt })
  }
}
