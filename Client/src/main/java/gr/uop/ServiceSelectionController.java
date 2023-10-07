package gr.uop;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ServiceSelectionController {

    private static LinkedHashMap<Service, ServiceSelectionServiceController> serviceMap = new LinkedHashMap<>();

    private double totalCost = 0;

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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @FXML
    private FlowPane serviceGroupHolder;

    @FXML
    private Label totalCostLabel;
    
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
        this.totalCostLabel.setText(totalCost);
    }

    public void updateTotalCostLabel(){
        setTotalCostLabel("Total Cost: " + getTotalCost() + " €");
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
        Parent root = FXMLLoader.load(getClass().getResource("SelectedServiceList.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
}
