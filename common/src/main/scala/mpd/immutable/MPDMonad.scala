package mpd
package immutable

import scala.util.Try
import scala.annotation.tailrec

import java.net.{ Socket => JSocket, InetSocketAddress }
import java.io.{ BufferedReader, InputStreamReader, OutputStream }

package object immutable {
  final case class MPDB(inCL: Boolean, flushed: Boolean)
  final case class MPDC(addr: InetSocketAddress, socket: JSocket, read: BufferedReader, out: OutputStream)
  final type MPDS = (MPDB, MPDC)

  

}

final object MPDInternal extends MPDInternalTypes
sealed trait MPDInternalTypes {
  val OKStr = "OK"
  val ACKStr = "ACK"
  val BuffSize = 1024
  val OKConnectr = s"""$OKStr MPD (.*)""".r
  val OKr = OKStr.r
  val ACKr = """$ACKStr [(.*)@(.*)] \{(.*)\}(.*)""".r
  val Encoding = "UTF-8"
  val Timeout = 10000
}

final object MPDCustom extends MPDCustomMonad
trait MPDCustomMonad {
  import scalaz._
  import Scalaz._
  import immutable._
  import MPDInternal._


  case class MPDState(flushed: Boolean)

  sealed trait MPDFailure
  final case class MPDUnknown(e: Exception) extends MPDFailure
  final case class MPDAuth(cmd: String) extends MPDFailure
  final case class MPDBogus(response: String) extends MPDFailure
  
  type MPD[+A] = StateT[({ type l[+X] = \/[MPDFailure, X] })#l, MPDState, A]

  final object MPD extends StateTFunctions with StateTInstances {
    def apply[A](f: MPDState => MPDFailure \/ (MPDState, A)): MPD[A] = new MPD[A] {
      def apply(s: MPDState) = f(s)
    }
  }

  def flush() = MPD[Unit] {
    x => (x copy (flushed = true), ()).right
  }

  def bogus(s: String) = MPD[String] {
    x => MPDBogus("I don't care: $s.").left
  }

  def mustflushed() = MPD[String] {
    case MPDState(false) => MPDBogus("Hey no flush!").left
    case x => (x, "Good!").right
  }

  def copycat(s: String) = MPD[String] {
    x => (x copy (flushed = false), s).right
  }

  def scen1(): MPD[String] = for {
    s1 <- copycat("heyo")
    _ <- flush
    _ <- mustflushed
    s2 <- copycat("hey again")
    _ <- flush
    _ <- mustflushed
  } yield (s1 + s2) // \/-((MPDState(true),heyohey again))

  def scen2(): MPD[String] = for {
    s1 <- copycat("heyo")
    _ <- flush
    _ <- mustflushed
    s2 <- copycat("hey again")
    _ <- mustflushed
  } yield (s1 + s2) // -\/(MPDBogus(Hey no flush!))

}

trait MPDOps {
  import scalaz._
  import Scalaz._
  import immutable._
  import MPDInternal._

  val CmdBegin = "command_list_begin"
  val CmdEnd = "command_list_end"

  def connect(addr: String, port: Int): MPDC = {
    val (s, i, o) = MPDCHelper.connect(addr, port)
    MPDC(new InetSocketAddress(addr, port), s, i, o)
  }

  def disconnect() = State[MPDS, Unit] {
    case (_, c@MPDC(a, s, r, o)) => {
      o.close
      r.close
      s.close

      ((MPDB(false, true), c), ())
    }
  }

  def reconnect(): State[MPDS, Unit] = for {
    _ <- disconnect
    state <- get[MPDS]
    (_, MPDC(a, _, _, _)) = state
    mpdc = connect(a.getHostName, a.getPort)
    _ <- put((MPDB(false, true), mpdc))
  } yield ()

  def write(cmd: String) = State[MPDS, Unit] {
    case (b, c@MPDC(_, _, _, o)) => { 
      o.write(cmd.getBytes(MPDInternal.Encoding))
      ((b copy (flushed = false), c), ())
    }
  }

  def writeln(cmd: String) = write(cmd + "\n")
  def clbegin() = writeln(CmdBegin)
  def clend() = writeln(CmdEnd)
 
  def read() = State[MPDS, Vector[String]] {
    case (b, c@MPDC(_, _, in, _)) => {
      @tailrec def readEnd(out: Vector[String]): Vector[String] = 
	in.readLine match {
          case null => throw MPDCException("empty line")
          case ACKr(err, num, cmd, msg) => throw MPDCAckException(err, num, cmd, msg)
          case OKr() => out
          case x => readEnd(out :+ x)
	}

      ((b, c), readEnd(Vector.empty))
    }
  }


  def flush() = State[MPDS, Unit] {
    case (b, c@MPDC(_, _, _, o)) => {
      o.flush
      ((b copy (flushed = true), c), ())
    }
  }

  def example(): State[MPDS, String] = for {
    _ <- write("HELLO GIVE ME DATA!")
    _ <- flush
    s <- read
    _ <- write("moar data")
    _ <- flush
    s2 <- read
  } yield "Got " + s.toString + " and " + s2.toString  
}
