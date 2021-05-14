import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.*; // for ArrayList
  
public class TimeProtocolFactory implements ProtocolFactory {

  static public final int BUFSIZE = 1024; // Size of receive buffer

  public Runnable createProtocol(final Socket clntSock, final Logger logger) {
    return new Runnable() {
      public void run() {
        TimeProtocolFactory.handleClient(clntSock, logger);
      }
    };
  }

  static private void handleClient(Socket clntSock, Logger logger) {
    ArrayList entry = new ArrayList();
    entry.add("Client address and port = " +
               clntSock.getInetAddress().getHostAddress() + ":" +
               clntSock.getPort());
    entry.add("Thread = " + Thread.currentThread().getName());
    try {

      Date date = new Date();
      Calendar calendar = GregorianCalendar.getInstance();
      calendar.setTime(date);
      int hour = calendar.get(Calendar.HOUR_OF_DAY);

      String greeting;
      if(hour<12){
          greeting = "Good morning!";
        } else if(hour >= 12 && hour <= 17) {
          greeting = "Good afternoon!";
        } else {
          greeting = "Good evening!";
      }

      entry.add(greeting);

      String datestring = greeting + "\n" + new Date() + ".";
      entry.add("Server date = " + datestring); // with space
      entry.add("Server^date = " + datestring); // with ^
      clntSock.getOutputStream().write( datestring.getBytes() );

    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    try {  // Close socket
      clntSock.close();
    } catch (IOException e) {
      entry.add("Exception = " +  e.getMessage());
    }

    logger.writeEntry(entry);
  }
}
