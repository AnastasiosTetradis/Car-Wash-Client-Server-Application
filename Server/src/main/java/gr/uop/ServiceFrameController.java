package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ServiceFrameController {
    
    @FXML
    private Label serviceGroupLabel;

    @FXML
    private Label serviceNameLabel;

    @FXML
    private Label servicePriceLabel;

    public Label getServiceGroupLabel() {
        return serviceGroupLabel;
    }

    public void setServiceGroupLabel(String serviceGroupLabel) {
        getServiceGroupLabel().setText(serviceGroupLabel);
    }

    public Label getServiceNameLabel() {
        return serviceNameLabel;
    }

    public void setServiceNameLabel(String serviceNameLabel) {
        getServiceNameLabel().setText(serviceNameLabel);
    }

    public Label getServicePriceLabel() {
        return servicePriceLabel;
    }

    public void setServicePriceLabel(String servicePriceLabel) {
        getServicePriceLabel().setText(servicePriceLabel);
    }

}
