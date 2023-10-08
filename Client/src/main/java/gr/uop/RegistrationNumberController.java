package gr.uop;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Iterator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RegistrationNumberController {

    @FXML
    private TextField registrationField;

    @FXML
    private Button continueButton;

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

    public Button getContinueButton() {
        return continueButton;
    }

    public void setContinueButton(Button continueButton) {
        this.continueButton = continueButton;
    }

    @FXML
    public void initialize(){
        registrationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String input = registrationField.getText().strip();
                if(input.length() < 2){
                    continueButton.setDisable(true);
                }
                else{
                    continueButton.setDisable(false);
                }
                System.out.println("reg number changed");
            }
        });
    }

    @FXML
    public void pressButton(ActionEvent event) throws IOException{
        Button pressedButton = (Button) event.getSource();
        String pressedButtonName = pressedButton.getText().strip();
        if(pressedButtonName.equalsIgnoreCase("_")){
            registrationField.setText(registrationField.getText() + " ");
        }
        else if(pressedButtonName.equalsIgnoreCase("backspace")){
            int inputLength = registrationField.getText().length();
            if(inputLength >= 1){
                registrationField.setText(registrationField.getText().substring(0, inputLength-1));
            }
        }
        else if(pressedButtonName.equalsIgnoreCase("enter")){
            switchToNextScene(event);
        }
        else if(pressedButtonName.equalsIgnoreCase("en")){
            
        }
        else{
            registrationField.setText(registrationField.getText() + pressedButtonName);
        }
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        // Add registration number to order
        Client.getCurrentOrder().setRegistrationNumber(this.registrationField.getText().strip());
        System.out.println("DEBUG: text is \"" + Client.getCurrentOrder().getRegistrationNumber() + "\"");
        
        // Get VehicleTypeSelection FXML
        FXMLLoader vehicleTypeLoader = new FXMLLoader(getClass().getResource("VehicleType.fxml"));
        Parent root = vehicleTypeLoader.load();
        VehicleTypeController vehicleTypeController = vehicleTypeLoader.getController();

        // For every available vehicle
        Iterator<Vehicle> vehicleIterator = Client.getDb().getAllVehicles().iterator();
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
