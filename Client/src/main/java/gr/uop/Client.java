package gr.uop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Client extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("RegistrationNumber.fxml"));
        // FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("VehicleType.fxml"));
        Parent mainPane = fxmlLoader.load();
        scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Client - Registration Input Page");

        stage.show();
        
        // Setting Max and Minimum Size
        stage.setMinWidth(1024);
        stage.setMinHeight(768);

        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
    }

    public static void main(String[] args) {
        launch();
    }

}