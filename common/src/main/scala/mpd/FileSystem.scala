package mpd

object FileSystem {
  case class URI(str: String)
  implicit def str2URI(s: String) = URI(s)
  implicit def URI2str(u: URI) = u.str
  sealed trait Entry
  case class Directory(uri: URI, entries: Seq[Entry]) extends Entry
  case class File(name: String) extends Entry
}
