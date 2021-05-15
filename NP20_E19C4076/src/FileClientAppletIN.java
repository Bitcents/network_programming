// 4.6 File Client Applet INPUT
import java.net.*;        // for Socket
import java.io.*;         // for OutputStream
import java.awt.event.*;  // for ActionListener
import javax.swing.*;     // for Swing components

public class FileClientAppletIN extends JApplet implements ActionListener {

  private int PORT = 5002; // Default port
  private JTextArea text;
  private JButton saveButton;

  public void init() {
    text = new JTextArea(108, 30);
    getContentPane().add(new JScrollPane(text), "Center");

    saveButton = new JButton("Save");
    getContentPane().add(saveButton, "South");
    saveButton.addActionListener(this);
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == saveButton) {
      String intext  = "";
      String sername = "";
      String ipaddr  = "";
      int    nlpos;

      intext = text.getText();

      nlpos   = intext.indexOf("\n");
      sername = intext.substring(0, nlpos);

      intext = intext.substring(nlpos+1, intext.length());

      nlpos  = intext.indexOf("\n");
      ipaddr = intext.substring(0, nlpos);

      intext = intext.substring(nlpos+1, intext.length());

      PORT   = Integer.parseInt(ipaddr);

      try {
        // Create socket connected to server on specified port
        Socket socket = new Socket(sername, PORT);
        //Socket socket = new Socket(getCodeBase().getHost(), PORT);

        socket.getOutputStream().write(intext.getBytes());
        //socket.getOutputStream().write(text.getText().getBytes());
     
        socket.close();
        text.setText(null);
      } catch (Exception e) {
        text.setText(null);
        text.append(e.getMessage());
      }
    }
  }

}
