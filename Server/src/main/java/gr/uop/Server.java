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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class Server extends Application {

    private static Scene scene;

    public static ObservableList<Order> readOrder(){
        URL url = Server.class.getResource("data/profit_file.txt");
        File file = new File(url.getPath());
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                Order order = new Order();
                String row[] = scanner.nextLine().split(";");
                // Set Reg Number
                order.setRegistrationNumber(row[0]);

                // Set VehicleType
                order.setVehicleType(row[1]);

                // Set Services
                String services[] = row[2].replace("[" , "").replace("]", "").split(",");
                for(String serviceRow :services){
                    String serviceData[] = serviceRow.split(":");

                    Service service = new Service();

                    service.setServiceName(serviceData[0]);
                    service.setServicePrice(Double.parseDouble(serviceData[1]));
                    order.addService(service);
                }

                // Set totalCost
                order.setTotalCost(Double.parseDouble(row[3]));

                orderList.add(order);

                System.out.println("Receive order:" + orderList.toString());
            }
        }
        catch(IOException e){
            System.out.println("Loop Stopped");
            System.out.println(orderList.get(0));
        }
        return orderList;
    }

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