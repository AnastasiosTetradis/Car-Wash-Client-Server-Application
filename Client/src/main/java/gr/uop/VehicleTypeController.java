package gr.uop;

import java.io.IOException;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class VehicleTypeController {
    


    @FXML
    private FlowPane vehicleButtonHolder;
    
    @FXML
    private Button continueButton;

    @FXML
    public void initialize(){
        Client.getVehicleTypeObserver().addListener((ListChangeListener.Change<? extends Vehicle> c) -> {
            if(Client.getVehicleTypeObserver().get(0) == null){
                System.out.println("Observer initialize value (null)");
                continueButton.setDisable(true);
                Client.getCurrentOrder().setVehicleType("");
            }
            else{
                Client.getCurrentOrder().setVehicleType(Client.getVehicleTypeObserver().get(0).getVehicleName());
                System.out.println("Observer initialize value" + Client.getVehicleTypeObserver().get(0).getVehicleName());
                continueButton.setDisable(false);
            }

            Client.getServiceObserver().clear();
        });
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
