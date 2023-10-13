package gr.uop;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServiceSelectionController {

    private static ObservableList<Service> serviceObserver = FXCollections.observableArrayList();
    private static LinkedHashMap<Service, ServiceSelectionServiceController> serviceMap = new LinkedHashMap<>();

    public static ObservableList<Service> getServiceObserver() {
        return serviceObserver;
    }

    public static void setServiceObserver(ObservableList<Service> serviceObserverList) {
        serviceObserver = serviceObserverList;
    }

    public static void setServiceObserver(Service service) {
        serviceObserver.set(0, service);
    }

    public static void addToServiceObserver(Service service){
        serviceObserver.add(service);
    }

    public static void removeFromServiceObserver(Service service){
        serviceObserver.remove(service);
    }

    public static LinkedHashMap<Service, ServiceSelectionServiceController> getServiceMap(){
        return serviceMap;
    }

    public static Set<Service> getServices(){
        return serviceMap.keySet();
    }

    public static Service getServiceByNameAndGroup(String serviceName, ServiceGroup serviceGroup){
        Iterator<Service> iterator = getServices().iterator();
        while(iterator.hasNext()){
            Service currentService = iterator.next();
            if(currentService.getServiceName().equals(serviceName) && currentService.getServiceGroup().equals(serviceGroup)){
                return currentService;
            }
        }
        return null;
    }

    public static Service getServiceByController(ServiceSelectionServiceController controller){
        Iterator<Service> iterator = getServices().iterator();
        while(iterator.hasNext()){
            Service currentService = iterator.next();
            ServiceSelectionServiceController currentController = getServiceController(currentService);
            if(currentController == controller){
                return currentService;
            }
        }
        return null;
    }

    public static ServiceSelectionServiceController getServiceController(Service service){
        return serviceMap.get(service);
    }

    public static void addService(Service service, ServiceSelectionServiceController controller){
        serviceMap.put(service, controller);
    }

    @FXML
    private FlowPane serviceGroupHolder;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Button continueButton;
    
    public FlowPane getServiceGroupHolder() {
        return serviceGroupHolder;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceGroupHolder(FlowPane serviceGroupHolder) {
    //     this.serviceGroupHolder = serviceGroupHolder;
    // }

    public void addToServiceGroupHolder(Node serviceGroupHolder){
        this.serviceGroupHolder.getChildren().add(serviceGroupHolder);
    }

    public Label getTotalCostLabel() {
        return totalCostLabel;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setTotalCost(Label totalCost) {
    //     this.totalCost = totalCost;
    // }

    public void setTotalCostLabel(String totalCost) {
        getTotalCostLabel().setText(totalCost);
    }

    public void updateTotalCostLabel(){
        setTotalCostLabel("Total Cost: " + String.format("%.2f", Client.getCurrentOrder().getTotalCost())  + " €");
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
        serviceObserver.addListener((ListChangeListener.Change<? extends Service> c) -> {
            updateTotalCostLabel();
            if(serviceObserver.size() >= 1){
                continueButton.setDisable(false);
            }
            else{
                continueButton.setDisable(true);
            }
        });
    }

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        // Get VehicleTypeSelection FXML
        FXMLLoader vehicleTypeLoader = new FXMLLoader(getClass().getResource("VehicleType.fxml"));
        Parent root = vehicleTypeLoader.load();
        VehicleTypeController vehicleTypeController = vehicleTypeLoader.getController();
        vehicleTypeController.getVehicleButtonHolder();

        // For every available vehicle
        Iterator vehicleIterator = Client.getDb().getAllVehicles().iterator();
        while(vehicleIterator.hasNext()){

            // Get vehicle
            Vehicle currentVehicle = (Vehicle) vehicleIterator.next();

            // Get VehicleTypeButton FXML
            FXMLLoader vehicleTypeButtonLoader = new FXMLLoader(getClass().getResource("vehicletype_button.fxml")); 
            Button button = vehicleTypeButtonLoader.load();
            VehicleTypeButtonController vehicleTypeButtonController = vehicleTypeButtonLoader.getController();

            // Set Up VehicleTypeButton FXML
            vehicleTypeButtonController.setVehicleName(currentVehicle.getVehicleName());

            String iconName = currentVehicle.getVehicleIcon().strip();
            if(!iconName.equals("")){
                vehicleTypeButtonController.setVehicleIcon(getClass().getResource("data/" + iconName).toString());
            }

            // Select based on selection
            if(vehicleTypeButtonController.getVehicleName().getText().equals(Client.getCurrentOrder().getVehicleType())){
                vehicleTypeButtonController.getSelectButton().setStyle("-fx-background-color: linear-gradient(to right, #47bb7c26, #4ACF9F26);-fx-background-radius: 11");      
            }
            
            // Add VehicleTypeButton FXML to VehicleTypeSelection FXML
            vehicleTypeController.addToVehicleButtonHolder(button);

            // Add Vehicle into Controllers Vehicle Set
            vehicleTypeController.addVehicle(currentVehicle, vehicleTypeButtonController);
        }

        // root = vehicleTypeLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        // Get SelectedServiceList FXML
        FXMLLoader selectedServiceListLoader = new FXMLLoader(getClass().getResource("SelectedServiceList.fxml"));
        Parent root = selectedServiceListLoader.load();
        SelectedServiceListController selectedServiceListController = selectedServiceListLoader.getController();

        // For every selected service
        Iterator<Service> serviceIterator = Client.getCurrentOrder().getServices().iterator();
        while(serviceIterator.hasNext()){

            // Get selected Service
            Service currentService = serviceIterator.next();

            // Get selectedServiceFrame FXML
            FXMLLoader selectedServiceLoader = new FXMLLoader(getClass().getResource("selectedservice_frame.fxml")); 
            HBox selectedServiceButton = selectedServiceLoader.load();
            SelectedServiceController selectedServiceController = selectedServiceLoader.getController();

            // Set up selectedServiceFrame FXML
            selectedServiceController.setServiceName(currentService.getServiceName());
            selectedServiceController.setServicePrice(currentService.getServicePrice() + " €");

            String iconName = currentService.getServiceGroup().getGroupIcon().strip();
            if(!iconName.equals("")){
                selectedServiceController.setGroupIcon(getClass().getResource("data/" + iconName).toString());
            }
            
            // Add selectedServiceFrame FXML to SelectedServiceList FXML
            selectedServiceListController.addToServiceHolder(selectedServiceButton);
            selectedServiceListController.setTotalCost("Total Cost: " + String.format("%.2f", Client.getCurrentOrder().getTotalCost()) + " €");
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}
