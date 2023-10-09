package gr.uop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class Server extends Application {

    private static Scene scene;

    private static OrderQueue orderQueue = new OrderQueue();

    public static OrderQueue getOrderQueue() {
        return orderQueue;
    }

    public static void setOrderQueue(OrderQueue orderQueue) {
        Server.orderQueue = orderQueue;
    }

    public static ObservableList<Order> readOrdersFromFile(){
        URL url = Server.class.getResource("data/profit_file.txt");
        File file = new File(url.getPath());
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                Order order = new Order();
                String row[] = scanner.nextLine().strip().split(";");
                // Set Reg Number
                order.setRegistrationNumber(row[0]);

                // Set VehicleType
                order.setVehicleType(row[1]);

                // Set Services
                String services[] = row[2].replace("[" , "").replace("]", "").split(",");
                for(String serviceRow :services){
                    String serviceData[] = serviceRow.split(":");

                    Service service = new Service();

                    // Set service name
                    service.setServiceName(serviceData[0]);

                    // Set service price
                    service.setServicePrice(Double.parseDouble(serviceData[1]));
                    
                    // Set service group (for the group name later)
                    ServiceGroup serviceGroup = new ServiceGroup();
                    serviceGroup.setGroupName(serviceData[2]);
                    service.setServiceGroup(serviceGroup);

                    order.addService(service);
                }

                // Set totalCost
                order.setTotalCost(Double.parseDouble(row[3]));

                orderList.add(order);

                System.out.println("Receive order:" + orderList.toString());
            }
            System.out.println("Orders found in file: " + orderList.size());
            scanner.close();
        }
        catch(IOException e){
            System.out.println("Loop Stopped !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(orderList.get(0));
        }
        return orderList;
    }

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("cashier.fxml"));
        Parent mainPane = fxmlLoader.load();

        orderQueue.setOrderListFromFile();
        orderQueue.getOrderList().remove(0);

        Scene scene = new Scene(mainPane, 1080, 700);
        stage.setScene(scene);
        stage.setTitle("Car Reception Register");

        stage.show();

        // Setting Max and Minimum Size
        stage.setMinWidth(1095);
        stage.setMinHeight(737);

        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
    }

    public static void main(String[] args) {
        // https://stackoverflow.com/questions/27311222/javafx-getting-class-cast-exception-in-css-for-blend-mode
        // https://bugs.openjdk.org/browse/JDK-8088468
        launch();
    }

}