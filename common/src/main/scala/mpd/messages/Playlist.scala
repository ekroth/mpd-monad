package mpd.messages

import scala.concurrent.Future

case class CurrentSong(file: String, time: Int, album: String, artist: String, title: String, track: Int, pos: Int, id: Int)

trait PlaylistMessages extends ServerMessages {
  /** Displays the metadata of the current song. */
  def currentsong(): Future[DefaultT[CurrentSong]]

  abstract override def required = super.required ++ Set(
    "add",
    "addid",
    "clear",
    "currentsong",
    "delete",
    "deleteid",
    "load",
    "rename",
    "move",
    "moveid",
    "playlistinfo",
    "playlistid",
    "plchanges",
    "plchangesposid",
    "rm",
    "save",
    "shuffle",
    "swap",
    "swapid",
    "listplaylist",
    "listplaylistinfo",
    "playlistadd",
    "playlistclear",
    "playlistdelete",
    "playlistmove",
    "playlistfind",
    "playlistsearch")
}
