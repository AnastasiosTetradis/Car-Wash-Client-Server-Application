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

    private static ObservableList<Vehicle> vehicleTypeObserver = FXCollections.observableArrayList((Vehicle)null);

    @FXML
    private FlowPane vehicleButtonHolder;
    
    @FXML
    private Button continueButton;

    @FXML
    public void initialize(){
        Vehicle vehicle = vehicleTypeObserver.get(0);

        vehicleTypeObserver.addListener((ListChangeListener.Change<? extends Vehicle> c) -> {
            
            if(vehicleTypeObserver.get(0) == null){
                System.out.println("Observer initialize value (null)");
                continueButton.setDisable(true);
                Client.getCurrentOrder().setVehicleType("");
            }
            else{
                Client.getCurrentOrder().setVehicleType(vehicleTypeObserver.get(0).getVehicleName());
                System.out.println("Observer initialize value" + vehicleTypeObserver.get(0).getVehicleName());
                continueButton.setDisable(false);
            }
        });

        // if(vehicle != null){
        //     getVehicleController(vehicle).select();
        // }
    }

    public static LinkedHashMap<Vehicle, VehicleTypeButtonController> getVehicleMap() {
        return vehicleMap;
    }

    public static Set<Vehicle> getVehicles() {
        return vehicleMap.keySet();
    }

    public void setVehicleMap(LinkedHashMap<Vehicle, VehicleTypeButtonController> vehicleMap) {
        this.vehicleMap = vehicleMap;
    }

    public void addVehicle(Vehicle vehicle, VehicleTypeButtonController controller) {
        vehicleMap.put(vehicle, controller);
    }

    public static VehicleTypeButtonController getVehicleController(Vehicle vehicle){
        return vehicleMap.get(vehicle);
    }

    public static Vehicle getVehicleByController(VehicleTypeButtonController controller){
        Iterator<Vehicle> iterator = getVehicles().iterator();
        while(iterator.hasNext()){
            Vehicle currentVehicle = iterator.next();
            VehicleTypeButtonController currentController = getVehicleController(currentVehicle);
            if(currentController == controller){
                return currentVehicle;
            }
        }
        return null;
    }

    public static ObservableList<Vehicle> getVehicleTypeObserver() {
        return vehicleTypeObserver;
    }

    public static void setVehicleTypeObserver(ObservableList<Vehicle> vehicleTypeObserverList) {
        vehicleTypeObserver = vehicleTypeObserverList;
    }

    public static void setVehicleTypeObserver(Vehicle vehicle) {
        vehicleTypeObserver.set(0, vehicle);
    }
    public static void addToVehicleTypeObserver(Vehicle vehicle){
        vehicleTypeObserver.add(vehicle);
    }
    
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
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        Client.switchToRegistrationPage();
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        Client.switchToServiceSelectionPage();
    }
}
