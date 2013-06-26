package mpd

trait MPDCSync extends MPDC {
  abstract override def connected = synchronized { super.connected }
  abstract override def writeb(f: => Unit) = synchronized { super.writeb(f) }
  abstract override def write(cmd: String) = synchronized { super.write(cmd) }
  abstract override def writef(cmd: String*) = synchronized { super.writef(cmd:_*) }
  abstract override def wread(cmd: String) = synchronized { super.wread(cmd) }
  abstract override def flush() = synchronized { super.flush }
  abstract override def clear() = synchronized { super.clear }
  abstract override def read() = synchronized { super.read }
  abstract override def disconnect() = synchronized { super.disconnect }
}
