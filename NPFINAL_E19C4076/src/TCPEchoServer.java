import javax.crypto.SecretKey;
import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServer {

  private static final int BUFSIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {
    int ccall = 0;
    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]); // port number

    // Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] byteBuffer = new byte[BUFSIZE];  // Receive buffer


    for (;;) { // Run forever, accepting and servicing connections
      Socket clntSock = servSock.accept();  // Get connection to Client
      ccall++;
      System.out.println("called count : " + ccall);
      System.out.println("Handling client at " +
        clntSock.getInetAddress().getHostAddress() + " on port " +
             clntSock.getPort());


      // Receive until client closes connection, indicated by -1 return

      BufferedReader br = new BufferedReader(new InputStreamReader(clntSock.getInputStream()));
      PrintWriter writer = new PrintWriter(clntSock.getOutputStream(), true);
      String inputLine;
      while((inputLine = br.readLine()) != null) {
          String decryptedText = Decrypter.decryptText(SecretKeyObtainer.getSecretKey(), inputLine);
          System.out.println(decryptedText);
        }


      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
