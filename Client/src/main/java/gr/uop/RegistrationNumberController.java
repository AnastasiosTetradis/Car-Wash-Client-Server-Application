package gr.uop;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @FXML
    private Button key_Enter;

    @FXML
    public void initialize(){
        registrationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String input = registrationField.getText().strip();
                if(input.length() < 2){
                    continueButton.setDisable(true);
                    key_Enter.setDisable(true);
                }
                else{
                    continueButton.setDisable(false);
                    key_Enter.setDisable(false);
                }
                System.out.println("reg number changed");
            }
        });

        if(!Client.getCurrentOrder().getRegistrationNumber().equals("")) {
            registrationField.setText(Client.getCurrentOrder().getRegistrationNumber());
        }
    }

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
    
    public Button getKey_Enter() {
        return key_Enter;
    }

    public void setKey_Enter(Button key_Enter) {
        this.key_Enter = key_Enter;
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
        Client.getCurrentOrder().setRegistrationNumber(registrationField.getText().strip());
        Client.switchToVehicleTypePage();
    }
}
