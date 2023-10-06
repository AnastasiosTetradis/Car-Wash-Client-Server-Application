
package gr.uop;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class VehicleTypeButtonController {
    
    @FXML
    private Label vehicleName;

    @FXML
    private ImageView vehicleIcon;

    @FXML
    private Button selectButton;

    public Label getVehicleName() {
        return vehicleName;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setVehicleName(Label vehicleName) {
    //     this.vehicleName = (vehicleName);
    // }

    public void setVehicleName(String vehicleName) {
        this.vehicleName.setText(vehicleName);
    }

    public ImageView getVehicleIcon() {
        return vehicleIcon;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setVehicleIcon(ImageView vehicleIcon) {
    //     this.vehicleIcon = vehicleIcon;
    // }

    public void setVehicleIcon(String iconPath) {
        this.vehicleIcon.setImage(new Image(iconPath));
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public void setSelectButton(String selectButton) {
        this.selectButton.setText(selectButton);
    }

    @FXML
    public void select(){
        // Deselect all buttons first
        Iterator<Vehicle> iterator = VehicleTypeController.getVehicleMap().keySet().iterator();
        while(iterator.hasNext()){
            VehicleTypeButtonController currentController = VehicleTypeController.getController(iterator.next());
            currentController.deselect();
        }

        // Selecting desired Vehicle button
        System.out.println("Selecting " + this.vehicleName.getText());
        Client.getCurrentOrder().setVehicleType(this.vehicleName.getText());
        System.out.println("Order's vehicle: " + Client.getCurrentOrder().getVehicleType());
        
        // Style code for selecting
        this.selectButton.setStyle("-fx-background-color: linear-gradient(to right, #47bb7c26, #4ACF9F26);-fx-background-radius: 11");      
    }

    public void deselect(){

        System.out.println("Deselecting " + this.vehicleName.getText());

        // Style code for deselecting
        this.selectButton.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 11");      

    }

}
