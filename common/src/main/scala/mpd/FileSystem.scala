package mpd

object FileSystem {
  type URI = String
  sealed trait Entry
  case class Directory(uri: URI, entries: Seq[Entry]) extends Entry
  case class File(name: String) extends Entry
}
