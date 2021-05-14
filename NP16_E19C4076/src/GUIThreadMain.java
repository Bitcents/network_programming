import java.net.*;        // for Socket and ServerSocket
import java.io.*;         // for IOException and Input/OutputStream
import java.awt.event.*;  // for WindowAdapter
import javax.swing.*;     // for ListModel and SwingUtilities

public class GUIThreadMain extends JFrame {

  public static void main(String[] args) throws Exception {
    if (args.length != 3)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): [<Optional properties>]"
                                       + " <Port> <Protocol> <Dispatcher>");

    int servPort = Integer.parseInt(args[0]);  // Server Port 
    String protocolName   = args[1];           // Protocol name
    String dispatcherName = args[2];           // Dispatcher name

    GUIThreadMain frame = new GUIThreadMain(servPort, protocolName, 
                                                    dispatcherName);
    frame.setVisible(true);

    ServerSocket servSock = new ServerSocket(servPort);
    Logger logger = new ConsoleLogger();       // Log messages to console
    ProtocolFactory protoFactory = (ProtocolFactory)  // Get protocol factory
      Class.forName(protocolName + "ProtocolFactory").newInstance();
    Dispatcher dispatcher = (Dispatcher)       // Get dispatcher
      Class.forName(dispatcherName + "Dispatcher").newInstance();

    dispatcher.startDispatching(servSock, logger, protoFactory);
    /* NOT REACHED */
  }

  public GUIThreadMain(int servPort, String protocolName, 
                           String dispatcherName) throws IOException {

    super(servPort + ":" + protocolName + ":" + dispatcherName); // Window title
    setSize(300, 300);  // Set the window size

    // Create the connection list
    getContentPane().add(new JScrollPane(
      new JList(GUIEchoProtocol.getListModel())), "Center");

    // Exit on window close
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    } );
  }
}
