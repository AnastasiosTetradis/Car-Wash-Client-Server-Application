package gr.uop;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RegistrationNumberController {

    @FXML
    private TextField registrationField;

    public TextField getRegistrationField() {
        return registrationField;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setRegistrationField(TextField registrationField) {
    //     this.registrationField = registrationField;
    // }

    public void setRegistrationFieldContent(String string){
        getRegistrationField().setText(string);
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        // Add registration number to order
        Client.getCurrentOrder().setRegistrationNumber(this.registrationField.getText());
        System.out.println("DEBUG: text is \"" + Client.getCurrentOrder().getRegistrationNumber() + "\"");
        
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
}
