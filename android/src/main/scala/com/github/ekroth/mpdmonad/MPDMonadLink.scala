package com.github.ekroth.mpdmonad

import scala.concurrent._

import android.content.Intent
import android.graphics.Color

import org.scaloid.common._

import mpd._

class MPDMonadLink extends SActivity {
  self =>
  import ExecutionContext.Implicits.global
  import AllInstances._


  onCreate {
    implicit val baseImplicit: Base = new Debug {
      override def debug(str: String) = info(str)
    }

    val intent = getIntent
    val str = intent.getStringExtra(Intent.EXTRA_TEXT)

    val b = implicitly[Base]
    val sc = implicitly[SC]

    val prefs = Preferences()
    val host = prefs.host("192.168.1.2")
    val port = prefs.port(6600)

    val f = future {
      info(s"connection to $host:$port")
      val con = b.Connect(host, port)

      con map { x =>
        info("connection successful")
        runOnUiThread { 
          toast(R.string.send_toast)
        }
        sc.load(SCURL(str)) run(x)
      }
    }

    f onComplete {
      case _ => runOnUiThread { finish() }
    }
  }
}
