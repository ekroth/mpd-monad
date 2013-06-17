package mpd

import scalaz._

import scala.annotation.tailrec

import java.net.{ Socket => JSocket }
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.BufferedReader

trait MPDConnectionTypes {
  val OK = "OK"
  val ACK = "ACK"
  val BuffSize = 1024

  // MAKE REGEXP FOR THIS
  // ACK [error@command_listNum] {current_command} message_text\n
  // OK MPD version\n
  val OKConnect = """OK MPD (.*)""".r
  val ACKConnect = """ACK \[(.*)@(.*)] {(.*)}(.*)""".r

  sealed trait Failure
  case class Disconnected() extends Failure
  case class ACKd() extends Failure
  case class Unknown(s: String) extends Failure

  import Scalaz._

  object MPDConnection {
    def connect(addr: String, port: Int): Failure \/ MPDConnection = {
      try {
	val socket = new JSocket(addr, port)
	val in = new BufferedReader(new InputStreamReader(socket.getInputStream), BuffSize)
	val line = in.readLine
	in.close
	OKConnect.findFirstIn(line) match {
	  case OKConnect(ver) => {
	   val out = new OutputStreamWriter(socket.getOutputStream, "UTF-8")
	   val in = new InputStreamReader(socket.getInputStream, "UTF-8")

	    MPDConnection(socket, in, out).right
	  }
	  case _ => ACKConnect.findFirstIn(line) match {
	    case x@ACKConnect(err, cmdNum, cmd, msg) => Unknown(x.toString).left
	    case _ => Unknown("unknown").left
	  }
	}
      } catch {
	case e: Throwable => Unknown(e.toString).left
      }
    }
  }

  case class MPDConnection(socket: JSocket, input: InputStreamReader, output: OutputStreamWriter) {
    def connected: Failure \/ Unit =
      if (socket.isConnected && !socket.isClosed()) ().right
      else Disconnected().left

    def write(cmd: String): Failure \/ Unit = {
      def write = try {
        output.write(cmd)
        output.flush.right
      } catch {
        case e: Throwable => Unknown(e.toString).left
      }

      for (
        _ <- connected;
        o <- write
      ) yield o
    }

    def read(): Failure \/ Vector[String] = {
      @tailrec def readEnd(br: BufferedReader, out: Vector[String]): Failure \/ Vector[String] = {
        val line = br.readLine

	line match {
	  case null => Unknown("empty read").left
	  case x if (x contains "ACK") => ACKd().left
	  case x if (x contains "OK") => out.right
	  case _ => readEnd(br, out :+ line)
	}
      }

      for (
        _ <- connected;
        o <- readEnd(new BufferedReader(input, BuffSize), Vector.empty)
      ) yield o
    }

    def disconnect(): Failure \/ Unit = {
      if (connected.isRight) {
	try {
	  socket.close.right
	} catch {
	  case e: Throwable => Unknown(e.toString).left
	}
      } else ().right
    }
  }
}
