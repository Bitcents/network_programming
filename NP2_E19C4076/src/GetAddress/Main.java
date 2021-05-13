package GetAddress;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("IP/Hostname Viewer");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
