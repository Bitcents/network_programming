import javax.crypto.SecretKey;
import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoClient {

  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

    String server = args[0]; // Server name or IP address
    // Convert input String to bytes using the default character encoding
    String plainTextMessage = args[1];

    int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    System.out.println("Connected to server...sending echo string");

    String encryptedMessage = Encrypter.encryptText(SecretKeyObtainer.getSecretKey(), plainTextMessage);
    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
    System.out.println("plain text: " + plainTextMessage + " encrypted text: " + encryptedMessage);
    writer.println(encryptedMessage);  // Send the encoded string to the server

    // Receive the same string back from the server

    socket.close();  // Close the socket and its streams
  }
}
