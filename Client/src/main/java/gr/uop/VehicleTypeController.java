package gr.uop;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class VehicleTypeController {

    private static LinkedHashMap<Vehicle, VehicleTypeButtonController> vehicleMap = new LinkedHashMap<>();

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
        Parent root = FXMLLoader.load(getClass().getResource("ServiceSelection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
