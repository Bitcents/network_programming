import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServer {

  public static void main(String[] args) throws IOException {
    int ccall = 0;
    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]); // port number

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message

    for (;;) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();  // Get connection to Client
      ccall++;
      System.out.println("called count : " + ccall);
      System.out.println("Handling client at " +
        clntSock.getInetAddress().getHostAddress() + " on port " +
             clntSock.getPort());

      InputStream  in  = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();
      DataInputStream dataIn = new DataInputStream(in);
      DataOutputStream dataOut = new DataOutputStream(out);


      String message = dataIn.read(); // Read the message from the input stream

      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = dataIn.read()) != -1)
        dataOut.writeUTF(message);
        dataOut.flush();

      dataOut.close();  
      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
