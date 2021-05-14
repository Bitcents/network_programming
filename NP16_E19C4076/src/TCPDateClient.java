import java.net.*; // for Socket
import java.io.*;  // for IOException and Input/OutputStream
  
public class TCPDateClient {

  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

    String server = args[0]; // Server name or IP address
    // Convert input String to bytes using the default character encoding
    //byte[] byteBuffer = args[1].getBytes();
    byte[] byteBuffer   = "Thu^Oct^11^12:13:00^JST^2020. ".getBytes();
    byte[] dummyBuf     = "0".getBytes();

    int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    //System.out.println("Connected to server...sending echo string");
    System.out.println("Connected to server : " + 
			server + " : port = " + servPort);

    InputStream  in  = socket.getInputStream();
    OutputStream out = socket.getOutputStream();

    // out.write(byteBuffer);  // Send the encoded string to the server

    // Receive the same string back from the server
    int totalBytesRcvd = 0; // Total bytes received so far
    int bytesRcvd;          // Bytes received in last read

    bytesRcvd = in.read(byteBuffer, 0, byteBuffer.length);

    System.out.println("Received: \n" + new String(byteBuffer));

    bytesRcvd = in.read(dummyBuf, 0, dummyBuf.length);

    socket.close();  // Close the socket and its streams
  }
}
