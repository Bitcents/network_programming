import java.net.*;      // for Socket, ServerSocket, and InetAddress
import java.io.*;       // for IOException and Input/OutputStream
import java.util.zip.*; // for GZIPOutputStream

public class CompressServer {

  public static final int BUFSIZE = 1024; // Size of read buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]); // Server port

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    byte[] buffer = new byte[BUFSIZE]; // Allocate read/write buffer
    int bytesRead;                     // Number of bytes read
    
    for (;;) { // Run forever, accepting and servicing connections
      // Wait for client to connect, then create a new Socket
      Socket clntSock = servSock.accept();

      System.out.println("Handling client at " +
        clntSock.getInetAddress().getHostAddress() + " on port " + clntSock.getPort());


      String message = "Welcome to the java file compression service";
      // Get the input and output streams from socket
      OutputStream clientOutput = clntSock.getOutputStream();
      InputStream in = clntSock.getInputStream();

      clientOutput.write(message.getBytes());


      GZIPOutputStream out = new GZIPOutputStream(clientOutput);

      // Receive until client closes connection, indicated by -1 return
      while ((bytesRead = in.read(buffer)) != -1)
        out.write(buffer, 0, bytesRead);

      out.close();      // Flush bytes from GZIPOutputStream
      clntSock.close(); // Close socket streams
    }
    /* NOT REACHED */
  }
}
