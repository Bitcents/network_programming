import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*;  // for IOException and Input/OutputStream

public class TCPFileServer {

  private static final int RCVBUFSIZE = 32; // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port> <File>");

    int servPort = Integer.parseInt(args[0]); // Server port
    String fileName = args[1];                // Name of file to save

    System.out.println("Server start at " + servPort + " to " + fileName + ";");

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize; // Size of received message
    byte[] echoBuffer = new byte[RCVBUFSIZE]; // Reception buffer

    for (;;) { // Run forever, accepting and servicing connections
      // Wait for client to connect, then create a new Socket
      Socket clntSock = servSock.accept();

      FileOutputStream file = new FileOutputStream(fileName);
      // File to write to

      System.out.println("Handling client at " +
        clntSock.getInetAddress().getHostAddress() + " on port " + clntSock.getPort());

      // Get the input stream from socket
      InputStream in = clntSock.getInputStream();

      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(echoBuffer)) != -1)
        file.write(echoBuffer, 0, recvMsgSize);

      clntSock.close();
      file.close();
    }
    /* NOT REACHED */
  }
}
