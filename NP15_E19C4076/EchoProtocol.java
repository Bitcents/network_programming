import java.net.*;   // for Socket
import java.io.*;    // for IOException and Input/OutputStream
import java.util.*;  // for ArrayList

class EchoProtocol implements Runnable {
  static public final int BUFSIZE = 32; // Size (in bytes) of I/O buffer

  private Socket clntSock;  // Connection socket
  private Logger logger;    // Logging facility

  public EchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger   = logger;
  }

  public void run() {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try {
      // Get the input and output I/O streams from socket

      InputStream  in  = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize;              // Size of received message
      int totalBytesEchoed = 0;     // Bytes received from client
      byte[] echoBuffer = new byte[BUFSIZE];  // Receive Buffer
      // Receive until client closes connection, indicated by -1
      while ((recvMsgSize = in.read(echoBuffer)) != -1) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    } catch (IOException  e) {
      entry.add("Exception = " +  e.getMessage());
    }

    try { // Close socket
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    logger.writeEntry(entry);
  }
}
