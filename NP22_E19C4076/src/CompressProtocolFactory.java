import java.net.*;      // for Socket
import java.io.*;       // for IOException and Input/OutputStream
import java.util.*;     // for ArrayList
import java.util.zip.*; // for GZIPOutputStream

public class CompressProtocolFactory implements ProtocolFactory {

  static public final int BUFSIZE = 1024; // Size of receive buffer


  public Runnable createProtocol(final Socket clntSock, final Logger logger) {
    return new Runnable() {
      public void run() {
        CompressProtocolFactory.handleClient(clntSock, logger);
      }
    };
  }


  static public void handleClient(Socket clntSock, Logger logger) {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try {
      // Get the input and output streams from socket
      String greeting = "Welcome to the java compression service";

      InputStream in = clntSock.getInputStream();
      OutputStream clientOutput = clntSock.getOutputStream();

      // Send a greeting to the client
      clientOutput.write(greeting.getBytes());

      GZIPOutputStream out = new GZIPOutputStream(clntSock.getOutputStream());

      byte[] buffer = new byte[BUFSIZE]; // Allocate read/write buffer
      int bytesRead;                     // Number of bytes read
      // Receive until client closes connection, indicated by -1 return
      while ((bytesRead = in.read(buffer)) != -1)
        out.write(buffer, 0, bytesRead);

      out.finish();      // Flush bytes from GZIPOutputStream
    } catch (IOException e) {
      logger.writeEntry("Exception = " +  e.getMessage());
    }

    try { // Close socket
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    logger.writeEntry(entry);
  }
}
