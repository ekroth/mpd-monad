package mpd.util

object MpdParse {
  def mapValues(v: Vector[String]) = {
    val r = """([-\w]*): (.*)""".r
    (r.findAllMatchIn(v.mkString("\n")) map {
      str: scala.util.matching.Regex.Match => (str.group(1).toLowerCase -> str.group(2))
    }).toMap
  }
}
