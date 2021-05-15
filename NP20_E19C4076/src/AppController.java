import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.Socket;

public class AppController {

    @FXML
    private TextField hostInput;

    @FXML
    private TextField portInput;

    @FXML
    private TextArea message;

    @FXML
    private Button sendButton;


    public void sendRequest(){
        try {
            // Get server name and port number from text field
            String server = hostInput.getText();
            int port = Integer.parseInt(portInput.getText());

            // Create socket connected to server on specified port
            Socket socket = new Socket(server, port);
            //Socket socket = new Socket(getCodeBase().getHost(), PORT);

            socket.getOutputStream().write(message.getText().getBytes());
            //socket.getOutputStream().write(text.getText().getBytes());

            socket.close();
            message.setText("");
        } catch (Exception e) {
            message.setText(e.getMessage());
        }

    }

}
