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
    private static Order currentOrder = new Order();

    public static ServiceDB getDb() {
        return db;
    }

    public static void setDb(ServiceDB db) {
        Client.db = db;
    }

    public static Order getCurrentOrder() {
        return currentOrder;
    }

    public static void setCurrentOrder(Order currentOrder) {
        Client.currentOrder = currentOrder;
    }
    

    @Override
    public void start(Stage stage) throws IOException {

        try{
            URL url = getClass().getResource("data/config.xml");
            File file = new File(url.getPath());

            db.addFromFile(file);
        }
        catch(InputMismatchException e){
            System.out.println("Wrong file format! Please rewrite config file carefully.");
        }
        System.out.println(db.toString()); // Debugging Test

        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("RegistrationNumber.fxml"));
        Parent mainPane = fxmlLoader.load();

        scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Client");

        stage.show();
        
        // Setting Max and Minimum Size
        stage.setMinWidth(1039);
        stage.setMinHeight(805);

        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
    }

    public static void main(String[] args) {
        launch();
    }

}