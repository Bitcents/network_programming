package GetAddress;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {

    @FXML
    private BorderPane bp;

    @FXML
    private TextArea resultArea;

    @FXML
    private HBox hb;

    @FXML
    private TextField inputField;

    @FXML
    private Button searchButton;

    @FXML
    protected void performSearch(){
        String input = inputField.getText();
        try{
            InetAddress result = InetAddress.getByName(input);
            String hostName = result.getHostName();
            String canonicalName = result.getCanonicalHostName();
            String hostAddress = result.getHostAddress();
            Boolean scope = result.isSiteLocalAddress();
            resultArea.setText("host name: " + hostName + "\n" +
                               "canonical name: " + canonicalName + "\n" +
                               "host address: " + hostAddress + "\n" +
                               "is site a local address: " + scope.toString()
            );
        } catch (UnknownHostException e) {
            resultArea.setText("Host could not be found.\nCheck that the hostname or ipaddress is correct.");
        }
    }

    @FXML
    protected void clearResult(){
        resultArea.setText("");
    }

}
