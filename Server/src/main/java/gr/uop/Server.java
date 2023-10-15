package gr.uop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class Server extends Application {

    private static Thread thread = null;

    private static OrderQueue orderQueue = new OrderQueue();

    public static OrderQueue getOrderQueue() {
        return orderQueue;
    }

    public static void setOrderQueue(OrderQueue orderQueue) {
        Server.orderQueue = orderQueue;
    }

    public static Thread getThread() {
        return thread;
    }

    public static void setThread(Thread thread) {
        Server.thread = thread;
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

                // Set Arrival DateTime
                if(row[4].equals("null")){
                    order.setArrivalDateTime(null);
                }
                else{
                    order.setArrivalDateTime(LocalDateTime.parse(row[4]));
                }

                // Set Departure DateTime
                if(row[5].equals("null")){
                    order.setDepartureDateTime(null);
                }
                else{
                    order.setDepartureDateTime(LocalDateTime.parse(row[5]));
                }
                

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

    public static void completeOrderInFile(Order order){
        //  Complete string for insertion
        String completeString = order.toCSV() + "\n";
        
        // Construct incomplete String (Replace last DateTime with null)
        String incompleteString = completeString.replace(completeString.split(";")[5], "");
        System.out.println("Removed token: " + completeString.split(";")[5]);
        for(String string :completeString.split(";")){
            System.out.println("Split token == " + string);
        }
        System.out.println("Incomplete String == " + incompleteString);
        replaceInFile(incompleteString, completeString);
        orderQueue.getOrderList().remove(order);
    }

    public static void deleteOrderInFile(Order order){
        //  Complete string for insertion
        String completeString = order.toCSV();
        String incompleteString = completeString.replace(completeString.split(";")[5], "");
        System.out.println("Split tokens == " + completeString.split(";"));
        System.out.println("Incomplete String == " + incompleteString);

        
        // Replace string with ""
        replaceInFile(incompleteString, "");
        orderQueue.getOrderList().remove(order);
    }

    public static void replaceInFile(String target, String replacement){
        URL url = Server.class.getResource("data/profit_file.txt");
        File file = new File(url.getPath());
        try{
            String fileString = "";
            Scanner scanner = new Scanner(file);

            //Storing all data from file into a string 
            while(scanner.hasNextLine()){
                String currentLine = scanner.nextLine() + "\n"; // Stupid nextLine() method takes my \n away >:(
                System.out.println("\n\nSearching line \"" + currentLine + "\" \nwith\n \"" + target + "\"\n\n");
                if(currentLine.contains(target)){
                    currentLine = replacement;
                    System.out.println("Writting replacement " + replacement);
                }
                fileString += currentLine;
            }

            scanner.close();

            System.out.println("File path == " + url.getPath());

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileString);
            fileWriter.flush();
            fileWriter.close();

            // OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(url.getPath()));
            // out.write(fileString);
            // out.flush();
            // out.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found. Please find it.");
        }
        catch(IOException e){
            System.out.println("File not found. Please find it.");
        }
    }

    public static void addOrderInFile(Order order){
        URL url = Server.class.getResource("data/profit_file.txt");
        File file = new File(url.getPath());
        try{
            String fileString = "";
            Scanner scanner = new Scanner(file);

            //Storing all data from file into a string 
            while(scanner.hasNextLine()){
                String currentLine = scanner.nextLine() + "\n"; // Stupid nextLine() method takes my \n away >:(
                fileString += currentLine;
            }

            fileString += order.toCSV();

            scanner.close();

            System.out.println("File path == " + url.getPath());

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileString);
            fileWriter.flush();
            fileWriter.close();

            System.out.println("Order added to file successfully.");
        }
        catch(FileNotFoundException e){
            System.out.println("File not found. Please find it.");
        }
        catch(IOException e){
            System.out.println("File not found. Please find it.");
        }
    }

    @Override
    public void start(Stage stage){
        try{
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;  // Debugging counter

                    // Checking if thread has been interrupted every time: https://stackoverflow.com/questions/20817980/java-thread-doesnt-stop-interrupt
                    while(!Thread.interrupted()){
                        // System.out.println("Thread running " + i);
                        // i++;
                        System.out.println("Scanning for orders...");
                        Server.scanForOrder();
                    }
                }
            });
            
        
            FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("cashier.fxml"));
            Parent mainPane = fxmlLoader.load();
            
            orderQueue.setOrderListFromFile();
            orderQueue.getOrderList().remove(0);
            
            Scene scene = new Scene(mainPane, 1080, 700);
            stage.setScene(scene);
            stage.setTitle("Car Reception Register");
            
            stage.show();
            thread.start();
            
            // Setting Max and Minimum Size
            stage.setMinWidth(1095);
            stage.setMinHeight(737);

            stage.setMaxWidth(1920);
            stage.setMaxHeight(1080);



            stage.setOnCloseRequest((e) -> {
                if(orderQueue.getOrderList().size() >= 1){
                    // Απλό παράθυρο μηνύματος (Alert). Δίνεται ο τύπος (INFORMATION, WARNING, ERROR).
                    Alert alert = new Alert(AlertType.INFORMATION);
        
                    // Βασικά στοιχεία του παραθύρου
                    alert.setTitle("Unproceeded Orders");
                    alert.setContentText("Please proceed all remaining orders first.");
                    alert.setHeaderText("Unproceeded Orders!");
        
                    // Αν θα μπλοκάρει το παράθυρο της εφαρμογής.
                    alert.initModality(Modality.WINDOW_MODAL);
        
                    // Ανάθεση του «γονικού» παραθύρου, ώστε να γνωρίζει ποιο θα μπλοκάρει.
                    alert.initOwner(stage);
        
                    // Δυνατότητα ελέγχου των κουμπιών που εμφανίζονται για το Alert.
                    alert.initStyle(StageStyle.DECORATED);
        
                    alert.show();

                    e.consume();
                    return;
                }
                thread.interrupt();
            });
        }
        catch(IOException e){
            System.out.println("IOException thrown!!!!");
        }
    }

    public static void scanForOrder(){
        try (ServerSocket serverSocket = new ServerSocket(9999);
             Socket clientSocket = serverSocket.accept();
             InputStream inputStream = clientSocket.getInputStream();
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);) {

            System.out.println("Accepted connection: " + clientSocket);

            // Receive object
            Object receivedObject =  objectInputStream.readObject();
            System.out.println("Received Object!");
            
            // Downcast object to order
            Order receivedOrder = (Order) receivedObject;

            // Add Arrival time to order
            receivedOrder.setArrivalDateTime(LocalDateTime.now());
            System.out.println("Received Order: " + receivedOrder.toCSV());
            // Server.getThread().interrupt(); // For debugging purposes 


            // Write down order in profit file
            addOrderInFile(receivedOrder);

            // Update service holder
            orderQueue.addToOrderList(receivedOrder);


        }
        catch (IOException e) {
            System.out.println("IOException found");
            System.out.println(e);
        }
        catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException found");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        // https://stackoverflow.com/questions/27311222/javafx-getting-class-cast-exception-in-css-for-blend-mode
        // https://bugs.openjdk.org/browse/JDK-8088468
        launch();
    }

}