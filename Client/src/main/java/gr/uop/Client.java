package gr.uop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;


/**
 * JavaFX App
 */
public class Client extends Application {

    private static Scene scene;
    private static ServiceDB db = new ServiceDB();

    
    @Override
    public void start(Stage stage) throws IOException {

        try{
            URL url = getClass().getResource("data/config.xml");
            File file = new File(url.getPath());

            db.addFromFile(file);
        }
        catch(InputMismatchException e){
            System.out.println("Wrong file format! Please rewrite config file carefuly.");
        }
        System.out.println(db.toString()); // Debugging Test

        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("RegistrationNumber.fxml"));
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