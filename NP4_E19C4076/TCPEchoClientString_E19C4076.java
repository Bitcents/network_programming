import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoClient {

  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3))  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

    String server = args[0]; // Server name or IP address
    // Store the message into a String variable for later use
    String message = new String(args[1]); // message

    int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    System.out.println("Connected to server...sending echo string");

    InputStream  in  = socket.getInputStream();
    OutputStream out = socket.getOutputStream();
    DataInputStream dataIn = new DataInputStream(in);
    DataOutputStream dataOut = new DataOutputStream(out);

    dataOut.writeUTF(message);  // Send the string to the server with UTF encoding

    // Receive the same string back from the server
    
    String receivedMessage = dataIn.readUTF();

    System.out.println("Received: " + receivedMessage);

    socket.close();  // Close the socket and its streams
  }
}
