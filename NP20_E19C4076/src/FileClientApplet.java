// 4.6 File Client Applet
import java.net.*;       // for Socket
import java.io.*;        // for OutputStream
import java.awt.event.*; // for ActionListener
import javax.swing.*;    // for Swing components

public class FileClientApplet extends JApplet implements ActionListener {

  private static final int PORT = 5050;  // Default port
  private JButton saveButton;
  private JTextArea text;

  public void init() {
    text = new JTextArea(8, 24);
    getContentPane().add(new JScrollPane(text), "Center");

    saveButton = new JButton("Save");
    getContentPane().add(saveButton, "South");
    saveButton.addActionListener(this);
  }

  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == saveButton) {
      try {
        // Create socket connected to server on specified port
        //Socket socket = new Socket("172.17.31.3", PORT);
        Socket socket = new Socket("192.168.0.2", PORT);

        socket.getOutputStream().write(text.getText().getBytes());
     
        socket.close();
        text.setText(null);
      } catch (Exception e) {
        text.setText(null);
        text.append(e.getMessage());
      }
    }
  }

}
