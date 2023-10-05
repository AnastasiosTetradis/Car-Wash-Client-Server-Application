package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class VehicleTypeButtonController {

    @FXML
    private Label vehicleName;

    @FXML
    private ImageView vehicleIcon;

    public Label getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(Label vehicleName) {
        this.vehicleName = (vehicleName);
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName.setText(vehicleName);
    }

    public ImageView getVehicleIcon() {
        return vehicleIcon;
    }

    public void setVehicleIcon(ImageView vehicleIcon) {
        this.vehicleIcon = vehicleIcon;
    }

}
