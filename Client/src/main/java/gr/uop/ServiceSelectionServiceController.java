package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceSelectionServiceController {
    
    @FXML
    private Label serviceName;

    @FXML
    private Label servicePrice;

    public Label getServiceName() {
        return serviceName;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceName(Label serviceName) {
    //     this.serviceName = serviceName;
    // }

    public void setServiceName(String serviceName) {
        this.serviceName.setText(serviceName);
    }

    public Label getServicePrice() {
        return servicePrice;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServicePrice(Label servicePrice) {
    //     this.servicePrice = servicePrice;
    // }

    public void setServicePrice(String servicePrice) {
        this.servicePrice.setText(servicePrice);
    }
}
