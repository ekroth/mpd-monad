package mpd

import scala.annotation.tailrec

import java.net.{ Socket => JSocket, InetSocketAddress }
import java.io._

import scalaz._

import mpd.messages._

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

import Scalaz._
import MPDInternal._

final object MPDConnection {
  def connect(addr: String, port: Int): DefaultT[MPDConnection] = {
    
    try {
      val socket = new JSocket()
      socket.connect(new InetSocketAddress(addr, port), Timeout)
      val in = new BufferedReader(new InputStreamReader(socket.getInputStream), BuffSize)
      val line = in.readLine
      line match {
        case OKConnectr(ver) => {
          val input = new BufferedReader(
            new InputStreamReader(socket.getInputStream, Encoding), BuffSize)
          val output = socket.getOutputStream()
          MPDConnection(socket, input, output).right
        }
        case ACKr(err, _, _, msg) => ACK(err, msg).left
        case x => Unknown(s"msg: $x").left
      }
    } catch {
      case e: Throwable => Unknown(e.toString).left
    }
  }
}

final case class MPDConnection(socket: JSocket, in: BufferedReader, out: OutputStream) {
  val CmdBegin = "command_list_begin"
  val CmdEnd = "command_list_end"

  /** Connection status.
   *
   * @return Unit if connected */
  def connected: PossibleError =
    if (socket.isConnected && !socket.isClosed() && in.ready) ().right
    else Disconnected().left

  /** Encapsulate f in command list */
  def writeb(f: => PossibleError): PossibleError =
    for {
      _ <- write(CmdBegin)
      _ <- f
      _ <- write(CmdEnd)
      _ <- flush
    } yield ()

  /** Write directly to socket, without command list */
  def write(cmd: String): PossibleError = try {
    out.write(s"""$cmd\n""".getBytes(MPDInternal.Encoding)).right
  } catch {
    case e: Throwable => Unknown(e.toString).left
  }

  /** Write and flush */
  def writef(cmd: String*) = writeb {
    write(cmd.mkString("\n"))
  }

  /** Write and read response */
  def wread(cmd: String): PossibleError =
    for {
      _ <- writef(cmd)
      _ <- read()
    } yield ()

  /** Flush output */
  def flush: PossibleError = {
    try {
      out.flush
      ().right
    } catch {
      case e: Throwable => Unknown(e.toString).left
    }
  }

  /** Clear input */
  def clear: PossibleError = try {
    in.skip(socket.getInputStream.available)
    ().right
  } catch {
    case e: Throwable => Unknown(e.toString).left
  }

  /** Read input, this is a blocking call */
  def read(): DefaultT[Vector[String]] = {
    @tailrec def readEnd(out: Vector[String]): DefaultT[Vector[String]] =
      in.readLine match {
        case null => Unknown("empty read").left
        case ACKr(err, _, _, msg) => ACK(err, msg).left
        case OKr() => out.right
        case x => readEnd(out :+ x)
      }

    try {
      readEnd(Vector.empty)
    } catch {
      case e: Throwable => Unknown(e.toString).left
    }
  }

  /** Disconnect */
  def disconnect(): PossibleError = {
    if (connected.isRight) {
      try {
        in.close.right
      } catch {
        case e: Throwable => Unknown(e.toString).left
      }
    } else ().right
  }
}

