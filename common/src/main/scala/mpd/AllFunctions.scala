package mpd

trait AllFunctions
  extends BaseFunctions
  with SoundCloudFunctions
  with DebugFunctions

final object AllFunctions extends AllFunctions
