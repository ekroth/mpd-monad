package mpd.messages

import mpd.ServerTypes

trait MessageTypes extends ServerTypes with AdminTypes with InfoTypes with PlaybackTypes
