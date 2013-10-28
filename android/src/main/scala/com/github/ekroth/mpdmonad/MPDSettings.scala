package com.github.ekroth.mpdmonad

import android.os.Bundle
import android.preference.PreferenceFragment

import org.scaloid.common._

class MPDSettings extends SActivity {

  onCreate {
    getFragmentManager().beginTransaction()
      .replace(android.R.id.content, new PreferenceFragment() {
        override def onCreate(b: Bundle) {
          super.onCreate(b)
          addPreferencesFromResource(R.xml.preferences)
        }
      }).commit();
    }
  }
