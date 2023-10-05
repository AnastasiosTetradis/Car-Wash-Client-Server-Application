package gr.uop;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
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

    public void setRegistrationField(TextField registrationField) {
        this.registrationField = registrationField;
    }

    public void setRegistrationFieldContent(String string){
        getRegistrationField().setText(string);
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("VehicleType.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
