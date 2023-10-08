package gr.uop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class Server extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("cashier.fxml"));
        Parent mainPane = fxmlLoader.load();

        Scene scene = new Scene(mainPane, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Client - Registration Input Page");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}