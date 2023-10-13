package gr.uop;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VehicleTypeController {
    
    private static LinkedHashMap<Vehicle, VehicleTypeButtonController> vehicleMap = new LinkedHashMap<>();
    private static ObservableList<String> vehicleTypeObserver = FXCollections.observableArrayList("");

    public static ObservableList<String> getVehicleTypeObserver() {
        return vehicleTypeObserver;
    }

    public static void setVehicleTypeObserver(ObservableList<String> vehicleTypeObserverList) {
        vehicleTypeObserver = vehicleTypeObserverList;
    }

    public static void setVehicleTypeObserver(String vehicleType) {
        vehicleTypeObserver.set(0, vehicleType);
    }

    public static void addToVehicleTypeObserver(String vehicleType){
        vehicleTypeObserver.add(vehicleType);
    }

    public static LinkedHashMap<Vehicle, VehicleTypeButtonController> getVehicleMap() {
        return vehicleMap;
    }

    public Set<Vehicle> getVehicles() {
        return vehicleMap.keySet();
    }

    public void setVehicleMap(LinkedHashMap<Vehicle, VehicleTypeButtonController> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    public void addVehicle(Vehicle vehicle, VehicleTypeButtonController controller) {
        vehicleMap.put(vehicle, controller);
    }

    public static VehicleTypeButtonController getController(Vehicle vehicle){
        return vehicleMap.get(vehicle);
    }

    @FXML
    private FlowPane vehicleButtonHolder;

    @FXML
    private Button continueButton;

    public FlowPane getVehicleButtonHolder() {
        return vehicleButtonHolder;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setVehicleButtonHolder(FlowPane vehicleButtonHolder) {
    //     this.vehicleButtonHolder = vehicleButtonHolder;
    // }

    public void addToVehicleButtonHolder(Node vehicleButtonHolder) {
        this.vehicleButtonHolder.getChildren().add(vehicleButtonHolder);
    }

    public Button getContinueButton() {
        return continueButton;
    }

    // public void setContinueButton(Button continueButton) {
    //     this.continueButton = continueButton;
    // }

    public void setContinueButton(String button){
        continueButton.setText(button);
    }

    @FXML
    public void initialize(){
        vehicleTypeObserver.addListener((ListChangeListener.Change<? extends String> c) -> {
            if(vehicleTypeObserver.get(0).equals("")){

                System.out.println("Observer initialize value" + vehicleTypeObserver.get(0));
                continueButton.setDisable(true);
            }
            else{
                System.out.println("Observer initialize value" + vehicleTypeObserver.get(0));
                continueButton.setDisable(false);
            }
        });
    }

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("RegistrationNumber.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        // Get ServiceSelection FXML
        FXMLLoader serviceSelectionLoader = new FXMLLoader(getClass().getResource("ServiceSelection.fxml"));
        Parent root = serviceSelectionLoader.load();
        ServiceSelectionController serviceSelectionController = serviceSelectionLoader.getController();

        // For every serviceGroup
        String selectedVehicleType = Client.getCurrentOrder().getVehicleType();
        Iterator<ServiceGroup> serviceGroupIterator = Client.getDb().getVehicleByName(selectedVehicleType).getServiceGroupList().iterator();
        while(serviceGroupIterator.hasNext()){
            // Get current Service Group
            ServiceGroup currentGroup = serviceGroupIterator.next();

            // Get current Service Group FXML
            FXMLLoader serviceGroupLoader = new FXMLLoader(getClass().getResource("servicegroup_frame.fxml")); 
            VBox groupVbox = serviceGroupLoader.load();
            ServiceSelectionGroupController serviceGroupController = serviceGroupLoader.getController();

            // Set up current Service Group FXML
            serviceGroupController.setGroupName(currentGroup.getGroupName());
            String iconName = currentGroup.getGroupIcon().strip();
            if(!iconName.equals("")){
                System.out.println("IconName=" + iconName);
                serviceGroupController.setGroupIcon(getClass().getResource("data/" + iconName).toString());
            }

            // Get all current group's services
            Iterator<Service> serviceIterator = currentGroup.getServices().iterator();
            while(serviceIterator.hasNext()){
                // Get current service
                Service currentService = serviceIterator.next();

                // Get current service FXML
                FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("service_button.fxml")); 
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

        System.out.println("FlowPanes children: " + serviceSelectionController.getServiceGroupHolder().getChildren().size());

        // root = vehicleTypeLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
