import java.net.*;    // for Socket
import java.io.*;     // for IOException and Input/OutputStream
import java.util.*;   // for ArrayList
import javax.swing.*; // for ListModel and SwingUtilities

public class GUIEchoProtocolFactory implements ProtocolFactory {
  public Runnable createProtocol(Socket clntSock, Logger logger) {
      return new GUIEchoProtocol(clntSock, logger);
  }
}

class GUIEchoProtocol implements Runnable {
  static public final int BUFSIZE = 32; // Size (in bytes) of I/O buffer

  private Socket clntSock;  // Connection socket
  private Logger logger;    // Logging facility
  private static DefaultListModel listModel = new DefaultListModel();
  private Runnable listDeletion;  // Runnable class for list deletion 

  public GUIEchoProtocol(Socket clntSock, Logger logger) {
    this.clntSock = clntSock;
    this.logger   = logger;

    try { // Thread-safe addition of item to list
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          // Since we are in an anonymous class, this refers to that class, not
          // the enclosing GUIEchoProtocol instance
          listModel.addElement(GUIEchoProtocol.this);
        }
      }
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    listDeletion = new Runnable() { // Runnable class for list deletion
      public void run() {
        // Since we are in an anonymous class, this refers to that class, not
        // the enclosing GUIEchoProtocol instance
        listModel.removeElement(GUIEchoProtocol.this);
      }
    };
  }

  public void run() {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
      clntSock.getInetAddress().getHostAddress() + ":" +
      clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());

    try {
      // Get the input and output I/O streams from socket
      InputStream in = clntSock.getInputStream();
      OutputStream out = clntSock.getOutputStream();

      int recvMsgSize;                        // Size of received message
      int totalBytesEchoed = 0;               // Bytes received from client
      byte[] echoBuffer = new byte[BUFSIZE];  // Receive Buffer
      // Receive until client closes connection, indicated by -1
      while ((recvMsgSize = in.read(echoBuffer)) != -1) {
        out.write(echoBuffer, 0, recvMsgSize);
        totalBytesEchoed += recvMsgSize;
        // ++ sleep in loop ++ 
        try {
           Thread.currentThread().sleep(30000);
        } catch (InterruptedException e) {
           entry.add("Exception = " +  e.getMessage());
        }
      }

      entry.add("Client finished; echoed " + totalBytesEchoed + " bytes.");
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    try {  // Close socket
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    logger.writeEntry(entry);

    try {  // Thread-safe deletion from list
      SwingUtilities.invokeAndWait(listDeletion);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }

  public static ListModel getListModel() {
    return listModel;
  }

  public String toString() {
    return clntSock.getInetAddress().getHostAddress() + ", " + clntSock.getPort();
  }
}
