package gr.uop;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class VehicleTypeController {

    @FXML
    private FlowPane vehicleButtonHolder;

    public FlowPane getVehicleButtonHolder() {
        return vehicleButtonHolder;
    }

    public void setVehicleButtonHolder(FlowPane vehicleButtonHolder) {
        this.vehicleButtonHolder = vehicleButtonHolder;
    }

    public void addToVehicleButtonHolder(Node vehicleButtonHolder) {
        this.vehicleButtonHolder.getChildren().addAll(vehicleButtonHolder);
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
