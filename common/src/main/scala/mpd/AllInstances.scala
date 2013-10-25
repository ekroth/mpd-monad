package mpd

trait AllInstances 
  extends BaseInstances
  with SCInstances
  with DebugInstances

final object AllInstances extends AllInstances
