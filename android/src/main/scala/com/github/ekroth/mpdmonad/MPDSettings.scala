package com.github.ekroth.mpdmonad

import org.scaloid.common._

import mpd._

class MPDSettings extends SActivity {
  import AllInstances._

  import scalaz._
  import Scalaz._

  onCreate {
    contentView = new SVerticalLayout {

      SEditTextPreference().key("host").title("Host!").summary("Yes").onPreferenceChange {
        (p, str) => true
      }

      SEditTextPreference().key("port").title("Port!").summary("Myes.").onPreferenceChange {
        (p, str) => try { str.toString.toInt; true } catch { case _: Throwable => false }
      }

      SButton("WAAII", { toast("yo") })

    } padding 20.dip
  }
}
