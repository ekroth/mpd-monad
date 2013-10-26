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

    val f = future {
      info("trying to connect")
      val con = b.Connect("192.168.1.2", 6600)

      con map { x =>
        info("connection successful")
        runOnUiThread { 
          toast(R.string.send_toast)
          toast(str)
        }
        sc.load(SCURL(str)) run(x)
   //     sc.load(SCURL("""https://soundcloud.com/steve-cobby/heeds""")) run(x)
      }
    }

    f onComplete {
      case _ => runOnUiThread { finish() }
    }

/*    contentView = new SVerticalLayout {
      style {
        case b: SButton => b.textColor(Color.RED).onClick(toast("Bang!"))
        case t: STextView => t textSize 10.dip
        case v => v.backgroundColor(Color.YELLOW)
      }

      STextView("I am 10 dip tall")
      STextView("Me too")
      STextView("I am taller than you") textSize 15.dip // overriding
      SEditText("Yellow input field")
      SButton("red")
    } padding 20.dip
 */


  }
}
