package object mpd {
  implicit class IntMpd(val v: Int) extends AnyVal {
    def toMpd = if (v == 0) true else false
  }
  implicit class BooleanMpd(val v: Boolean) extends AnyVal {
    def toMpd = if (v) 1 else 0
  }
}
