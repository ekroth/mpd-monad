package mpd

import scala.annotation.tailrec

import java.net.{ Socket => JSocket, InetSocketAddress }
import java.io.{ BufferedReader, InputStreamReader, OutputStream }

import scalaz._

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

case class MPDCException(msg: String) extends Exception
case class MPDCAckException(err: String, num: String, cmd: String, msg: String) extends Exception

final object MPDCHelper {
  def connect(addr: String, port: Int) = {    
    val socket = new JSocket()
    socket.connect(new InetSocketAddress(addr, port), Timeout)
    val in = new BufferedReader(new InputStreamReader(socket.getInputStream), BuffSize)
    val line = in.readLine
    line match {
      case OKConnectr(ver) => {
        val input = new BufferedReader(
          new InputStreamReader(socket.getInputStream, Encoding), BuffSize)
        val output = socket.getOutputStream()
        (socket, input, output)
      }
      case ACKr(err, num, cmd, msg) => throw MPDCAckException(err, num, cmd, msg)
      case x => throw MPDCException(s"Unknown response: $x")
    }
  }

}

trait MPDC {
  final val CmdBegin = "command_list_begin"
  final val CmdEnd = "command_list_end"

  /** Connection status. */
  def connected: Boolean

  /** Encapsulate f in command list */
  def writeb(f: => Unit) {
    write(CmdBegin)
    f
    write(CmdEnd)
    flush
  }

  /** Write directly to socket, without command list */
  def write(cmd: String): Unit

  /** Write and flush */
  def writef(cmd: String*) = writeb {
    write(cmd.mkString("\n"))
  }

  /** Write and read response */
  def wread(cmd: String): Vector[String] = {
      writef(cmd)
      read()
  }

  /** Flush output */
  def flush(): Unit

  /** Clear input */
  def clear(): Unit

  /** Read input, this is a blocking call */
  def read(): Vector[String]

  /** Disconnect */
  def disconnect(): Unit
}

case class MPDCStd(addr: String, port: Int) extends MPDC {
  val (socket, in, out) = MPDCHelper.connect(addr, port)

  /** Connection status. */
  override def connected = socket.isConnected && !socket.isClosed() && in.ready

  /** Write directly to socket, without command list */
  override def write(cmd: String) = out.write(s"""$cmd\n""".getBytes(MPDInternal.Encoding))

  /** Flush output */
  override def flush() = out.flush

  /** Clear input */
  override def clear() = in.skip(socket.getInputStream.available)

  /** Read input, this is a blocking call */
  override def read(): Vector[String] = {
    @tailrec def readEnd(out: Vector[String]): Vector[String] = 
    in.readLine match {
        case null => throw MPDCException("empty line")
        case ACKr(err, num, cmd, msg) => throw MPDCAckException(err, num, cmd, msg)
        case OKr() => out
        case x => readEnd(out :+ x)
      }

    readEnd(Vector.empty)
  }

  /** Disconnect */
  override def disconnect() = 
    if (connected) { 
      in.close.right
    }
}
