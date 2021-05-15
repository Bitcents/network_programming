import java.net.*; // for Socket
import java.io.*;  // for IOException and [File]Input/OutputStream

public class CompressClient implements Runnable{

  public static final int BUFSIZE = 256; // Size of read buffer

  String serverName;
  String fileName;
  int port;
  int id;

  CompressClient(String serverName, int port, String fileName, int idNumber){
    this.serverName = serverName;
    this.fileName = fileName;
    this.port = port;
    this.id = idNumber;
  }

  public static void main(String[] args) throws IOException {

    if (args.length != 3)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File>");

    String server = args[0];              // Server name or IP address
    int port = Integer.parseInt(args[1]); // Server port
    String filename = args[2];            // File to read data from xx.txt


    for(int i=0; i<5; i++){
      CompressClient client = new CompressClient(server, port, filename, i);
      Thread t = new Thread(client);
      t.start();
    }

  }



  @Override
  public void run(){
    try{
      FileInputStream fileIn = new FileInputStream(fileName);
      FileOutputStream fileOut = new FileOutputStream(fileName + id + ".gz");

      // Create socket connected to server on specified port
      Socket sock = new Socket(serverName, port);

      // Send uncompressed byte stream to server
      sendBytes(sock, fileIn); // call send method

      // Receive compressed byte stream from server
      InputStream sockIn = sock.getInputStream();
      int bytesRead;                     // Number of bytes read
      byte[] buffer = new byte[BUFSIZE]; // Byte buffer
      while ((bytesRead = sockIn.read(buffer)) != -1) { // get compressed data
        fileOut.write(buffer, 0, bytesRead);            // write into compressed file
        System.out.println("read buffer  ::" + buffer); // +Reading progress indicator
        System.out.println("bytes length::" + bytesRead); // +Reading progress indicator

        //System.out.print("R"); // Reading progress indicator
      }
      System.out.println("end of get compressed data."); // +End progress indicator line

      sock.close();     // Close the socket and its streams
      fileIn.close();   // Close file streams
      fileOut.close();

    } catch (IOException e){
      e.printStackTrace();
    }
  }


  private static void sendBytes(Socket sock, InputStream fileIn)
          throws IOException {
    OutputStream sockOut = sock.getOutputStream();
    int bytesRead;                     // Number of bytes read
    byte[] buffer = new byte[BUFSIZE]; // Byte buffer
    while ((bytesRead = fileIn.read(buffer)) != -1) { // get from text file
      sockOut.write(buffer, 0, bytesRead);            // send to stream socket
      System.out.println("read from file and send data "); // Writing progress indicator
      String xx = new String(buffer, "UTF-8"); // +encode buffer into UTF-8
      System.out.println(xx);                  // +and print normal text
    }
    sock.shutdownOutput(); // Done sending
  }
}
