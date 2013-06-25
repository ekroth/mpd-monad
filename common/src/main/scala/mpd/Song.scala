package mpd

case class Song(
  file: Option[String], lastModified: Option[String], 
  time: Option[Int], title: Option[String], 
  artist: Option[String], album: Option[String], 
  albumArtist: Option[String], genre: Option[String], 
  date: Option[String], composer: Option[Seq[String]], 
  disc: Option[String], track: Option[String],
  pos: Option[Int], id: Option[Int])
