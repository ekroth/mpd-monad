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
    val r = R.string.soundcloud_reg.r2String.r

    r findFirstIn str match {
      case Some(_) => {
        val b = implicitly[Base]
        val sc = implicitly[SC]

        val prefs = Preferences()
        val host = prefs.host("127.0.0.1")
        val port = prefs.port("6600").toInt

        future {
          val con = b.Connect(host, port)

          con map { x =>
            info("connection successful")
            sc.load(SCURL(str)) run(x)
          }

          runOnUiThread {
            toast(
              if (con.isRight) R.string.send_ok
              else R.string.send_fail)
            finish()
          }
        }
      }
      case _ => toast("invalid soundcloud link"); finish()
    }
  }
}
