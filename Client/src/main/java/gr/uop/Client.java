package gr.uop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Iterator;


/**
 * JavaFX App
 */
public class Client extends Application {

    private static Scene scene = new Scene(new Pane(), 1024, 768);
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

        
        switchToRegistrationPage();
       
        stage.setScene(scene);
        stage.setTitle("Client");

        stage.show();
        
        // Setting Max and Minimum Size
        stage.setMinWidth(1039);
        stage.setMinHeight(805);

        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
    }

    public static void switchToRegistrationPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("RegistrationNumber.fxml"));
            Parent root = fxmlLoader.load();
            scene.setRoot(root);
        }
        catch(IOException e) {
            System.out.println("ERROR: Registration Page could not be loaded.");
        }
    }

    public static void switchToVehicleTypePage() {
        try {
            // Get VehicleTypeSelection FXML
            FXMLLoader vehicleTypeLoader = new FXMLLoader(Client.class.getResource("VehicleType.fxml"));
            Parent root = vehicleTypeLoader.load();
            VehicleTypeController vehicleTypeController = vehicleTypeLoader.getController();
            // vehicleTypeController.getVehicleButtonHolder();

            // For every available vehicle
            Iterator<Vehicle> vehicleIterator = Client.getDb().getAllVehicles().iterator();
            while(vehicleIterator.hasNext()){

                // Get vehicle
                Vehicle currentVehicle = vehicleIterator.next();

                // Get VehicleTypeButton FXML
                FXMLLoader vehicleTypeButtonLoader = new FXMLLoader(Client.class.getResource("vehicletype_button.fxml")); 
                Button button = vehicleTypeButtonLoader.load();
                VehicleTypeButtonController vehicleTypeButtonController = vehicleTypeButtonLoader.getController();

                // Set Up VehicleTypeButton FXML
                vehicleTypeButtonController.setVehicleName(currentVehicle.getVehicleName());

                String iconName = currentVehicle.getVehicleIcon().strip();
                if(!iconName.equals("")){
                    vehicleTypeButtonController.setVehicleIcon(Client.class.getResource("data/" + iconName).toString());
                }

                // Select based on selection
                if(vehicleTypeButtonController.getVehicleName().getText().equals(Client.getCurrentOrder().getVehicleType())){
                    vehicleTypeButtonController.getSelectButton().setStyle("-fx-background-color: linear-gradient(to right, #47bb7c26, #4ACF9F26);-fx-background-radius: 11");      
                }
                
                // Add VehicleTypeButton FXML to VehicleTypeSelection FXML
                vehicleTypeController.addToVehicleButtonHolder(button);

                // Add Vehicle into Controllers Vehicle Set
                vehicleTypeController.addVehicle(currentVehicle, vehicleTypeButtonController);
                
                if(Client.getCurrentOrder().getVehicleType().equals(currentVehicle.getVehicleName())) {
                    vehicleTypeController.setVehicleTypeObserver(currentVehicle);
                    // vehicleTypeButtonController.select();
                }
            }

            // root = vehicleTypeLoader.load();
            scene.setRoot(root);
        }
        catch(IOException e) {
            System.out.println("ERROR: Vehicle Type Page could not be loaded.");
        }
    }

    public static void switchToServiceSelectionPage() {
        try {
            // Get ServiceSelection FXML
            FXMLLoader serviceSelectionLoader = new FXMLLoader(Client.class.getResource("ServiceSelection.fxml"));
            Parent root = serviceSelectionLoader.load();
            ServiceSelectionController serviceSelectionController = serviceSelectionLoader.getController();

            // For every serviceGroup
            String selectedVehicleType = Client.getCurrentOrder().getVehicleType();
            Iterator<ServiceGroup> serviceGroupIterator = Client.getDb().getVehicleByName(selectedVehicleType).getServiceGroupList().iterator();
            while(serviceGroupIterator.hasNext()){
                // Get current Service Group
                ServiceGroup currentGroup = serviceGroupIterator.next();

                // Get current Service Group FXML
                FXMLLoader serviceGroupLoader = new FXMLLoader(Client.class.getResource("servicegroup_frame.fxml")); 
                VBox groupVbox = serviceGroupLoader.load();
                ServiceSelectionGroupController serviceGroupController = serviceGroupLoader.getController();

                // Set up current Service Group FXML
                serviceGroupController.setGroupName(currentGroup.getGroupName());
                String iconName = currentGroup.getGroupIcon().strip();
                if(!iconName.equals("")){
                    System.out.println("IconName=" + iconName);
                    serviceGroupController.setGroupIcon(Client.class.getResource("data/" + iconName).toString());
                }

                // Get all current group's services
                Iterator<Service> serviceIterator = currentGroup.getServices().iterator();
                while(serviceIterator.hasNext()){
                    // Get current service
                    Service currentService = serviceIterator.next();

                    // Get current service FXML
                    FXMLLoader serviceLoader = new FXMLLoader(Client.class.getResource("service_button.fxml")); 
                    VBox serviceVbox = serviceLoader.load();
                    ServiceSelectionServiceController currentServiceController = serviceLoader.getController();

                    // Set up current service FXML
                    currentServiceController.setServiceName(currentService.getServiceName());
                    currentServiceController.setServicePrice(currentService.getServicePrice() + " €");
                    
                    // Add service FXML to ServiceGroup FXML
                    serviceGroupController.addToServiceHolder(serviceVbox);

                    
                    // Add service with its controller to serviceMap
                    ServiceSelectionController.addService(currentService, currentServiceController);
                    System.out.println("In serviceMap: " + currentService);
                }

                // Add ServiceGroup FXML to ServiceSelection FXML
                serviceSelectionController.addToServiceGroupHolder(groupVbox);
                serviceSelectionController.updateTotalCostLabel();
            }

            // Reselect all already selected services 
            Iterator<Service> serviceIterator = ServiceSelectionController.getServices().iterator();
            while(serviceIterator.hasNext()){
                Service currentService = serviceIterator.next();
                if(Client.getCurrentOrder().containsService(currentService)){
                    ServiceSelectionController.getServiceController(currentService).reselect();
                }
            }

            System.out.println("FlowPanes children: " + serviceSelectionController.getServiceGroupHolder().getChildren().size());
            scene.setRoot(root);
        }
        catch(IOException e) {
            System.out.println("ERROR: Service Selection Page could not be loaded.");
        }
    }

    public static void switchToSelectedServiceListPage() {
        try {
            // Get SelectedServiceList FXML
            FXMLLoader selectedServiceListLoader = new FXMLLoader(Client.class.getResource("SelectedServiceList.fxml"));
            Parent root = selectedServiceListLoader.load();
            SelectedServiceListController selectedServiceListController = selectedServiceListLoader.getController();

            // For every selected service
            Iterator<Service> serviceIterator = Client.getCurrentOrder().getServices().iterator();
            while(serviceIterator.hasNext()){

                // Get selected Service
                Service currentService = serviceIterator.next();

                // Get selectedServiceFrame FXML
                FXMLLoader selectedServiceLoader = new FXMLLoader(Client.class.getResource("selectedservice_frame.fxml")); 
                HBox selectedServiceButton = selectedServiceLoader.load();
                SelectedServiceController selectedServiceController = selectedServiceLoader.getController();

                // Set up selectedServiceFrame FXML
                selectedServiceController.setServiceName(currentService.getServiceName());
                selectedServiceController.setServicePrice(currentService.getServicePrice() + " €");

                String iconName = currentService.getServiceGroup().getGroupIcon().strip();
                if(!iconName.equals("")){
                    selectedServiceController.setGroupIcon(Client.class.getResource("data/" + iconName).toString());
                }
                
                // Add selectedServiceFrame FXML to SelectedServiceList FXML
                selectedServiceListController.addToServiceHolder(selectedServiceButton);
                selectedServiceListController.setTotalCost("Total Cost: " + String.format("%.2f", Client.getCurrentOrder().getTotalCost()) + " €");
            }

            scene.setRoot(root);
        }
        catch(IOException e) {
            System.out.println("ERROR: Selected Service Page could not be loaded.");
        }
    }

    public static void switchToThankYouPage() {
        try {
            // Connecting to Server

            // Sending Order
            Client.getCurrentOrder().setArrivalDateTime(LocalDateTime.now());
            System.out.println(Client.getCurrentOrder().toCSV());

            // Reseting order
            Client.setCurrentOrder(new Order());
            System.out.println("Client order reset!");


            // Get ThankYouSelection FXML
            FXMLLoader thankYouLoader = new FXMLLoader(Client.class.getResource("ThankYou.fxml"));
            Parent root = thankYouLoader.load();
            // ThankYouController thankYouController = thankYouLoader.getController();

            scene.setRoot(root);
        }
        catch(IOException e) {
            System.out.println("ERROR: Selected Service Page could not be loaded.");
        }
    }

    
    public static void main(String[] args) {
        launch();
    }

}