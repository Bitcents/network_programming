import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoClient {

  public static void main(String[] args) throws IOException {

    if (args.length < 3)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Port> <Messsage with Space>");
      //with this setting, the client will have to provide a port number as well. Previously this was optinal


    String server = args[0]; // Server name or IP address
    int servPort = Integer.parseInt(args[1]); // Server port number
    final int startIndex = 2; // Index of the first word in the message
    int lastIndex = args.length()-1 // Index of the last word in the message
    int currentIndex; // Index used to go through the message one word at a time
    String wholeMessage = ""; // This is used to store the message as it is sent out one word at a time
    ByteArrayOutputStream messageBytes = new ByteArrayOutputStream(); // This is to add they bytes one at a time

    
    

    // Create socket that is connected to server on specified port
    Socket socket = new Socket(server, servPort);
    System.out.println("Connected to server...sending echo string");

    InputStream  in  = socket.getInputStream();
    OutputStream out = socket.getOutputStream();

    currentIndex = startIndex;
    while(currentIndex <= lastIndex) // Loop through the message one word at a time
    {
        messageBytes.read(args[currentIndex].getBytes); // Add arguments to byte stream
        String part = new String(args[currentIndex]);   // This is for display purposes, does not change functionality
        wholeMessage += part;
        System.out.println("next word: " + part + "\n" + wholeMessage);
    }

    // Convert input String to bytes using the default character encoding
    byte[] byteBuffer = messageBytes.toByteArray(); // message obtained from byte stream

    out.write(byteBuffer);  // Send the encoded string to the server

    // Receive the same string back from the server
    int totalBytesRcvd = 0;  // Total bytes received so far
    int bytesRcvd;           // Bytes received in last read
    while (totalBytesRcvd < byteBuffer.length) {
      if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,  
                        byteBuffer.length - totalBytesRcvd)) == -1)
        throw new SocketException("Connection closed prematurely");
      totalBytesRcvd += bytesRcvd;
    }

    System.out.println("Received: " + new String(byteBuffer));

    socket.close();  // Close the socket and its streams
  }
}


/*

作成者：サリム・シディック・ハビブ
学籍番号：E19C4076
クラスE2P1

*/