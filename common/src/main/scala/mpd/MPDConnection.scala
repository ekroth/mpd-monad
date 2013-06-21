package mpd

import scalaz._

import scala.annotation.tailrec

import java.net.{ Socket => JSocket }
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.BufferedReader

final object MPDInternal extends MPDInternalTypes
trait MPDInternalTypes {
  val OK = "OK"
  val ACK = "ACK"
  val BuffSize = 1024
  val OKConnect = """OK MPD (.*)""".r
  val ACKConnect = """ACK [(.*)@(.*)] \{(.*)\}(.*)""".r

  sealed trait Fail
  case class Disconnected() extends Fail
  case class ACKd() extends Fail
  case class Unknown(s: String) extends Fail
}

import Scalaz._
import MPDInternal._

final object MPDConnection {
  def connect(addr: String, port: Int): Fail \/ MPDConnection = {
    try {
      val socket = new JSocket(addr, port)
      val in = new BufferedReader(new InputStreamReader(socket.getInputStream), BuffSize)
      val line = in.readLine
      line match {
        case OKConnect(ver) => {
          val out = new OutputStreamWriter(socket.getOutputStream, "UTF-8")
          val in = new InputStreamReader(socket.getInputStream, "UTF-8")

          MPDConnection(socket, in, out).right
        }
        case x @ ACKConnect(err, cmdNum, cmd, msg) => Unknown(x.toString).left
        case x => Unknown(s"msg: $x").left
      }
    } catch {
      case e: Throwable => Unknown(e.toString).left
    }
  }
}

final case class MPDConnection(socket: JSocket, input: InputStreamReader, output: OutputStreamWriter) {
  val CmdBegin = "command_list_begin"
  val CmdEnd = "command_list_end"

  def connected: Fail \/ Unit =
    if (socket.isConnected && !socket.isClosed()) ().right
    else Disconnected().left

  def batch[T](f: => T): Fail \/ T = {

    for {
      _ <- write(CmdBegin)
      v = f
      _ <- write(CmdEnd)
      _ = output.flush
    } yield v
  }

  def write(cmd: String): Fail \/ Unit = try {
    output.write(s"""$cmd\n""").right
  } catch {
    case e: Throwable => Unknown(e.toString).left
  }

  def flush = output.flush

  def read(): Fail \/ Vector[String] = {
    @tailrec def readEnd(br: BufferedReader, out: Vector[String]): Fail \/ Vector[String] =
      br.readLine match {
        case null => Unknown("empty read").left
        case x if (x contains "ACK") => ACKd().left
        case x if (x contains "OK") => out.right
        case x => readEnd(br, out :+ x)
      }

    readEnd(new BufferedReader(input, BuffSize), Vector.empty)
  }

  def disconnect(): Fail \/ Unit = {
    if (connected.isRight) {
      try {
        socket.close.right
      } catch {
        case e: Throwable => Unknown(e.toString).left
      }
    } else ().right
  }
}

