package mpd

trait AllInstances 
  extends BaseInstances
  with SoundCloudInstances

final object AllInstances extends AllInstances
