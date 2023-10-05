package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SelectedServiceController {
    
    @FXML
    private Label serviceName;

    @FXML
    private Label servicePrice;

    @FXML
    private Label groupName;

    @FXML
    private ImageView groupIcon;

    public Label getServiceName() {
        return serviceName;
    }

    public void setServiceName(Label serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName.setText(serviceName);
    }

    public Label getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Label servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice.setText(servicePrice);
    }

    public Label getGroupName() {
        return groupName;
    }

    public void setGroupName(Label groupName) {
        this.groupName = groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName.setText(groupName);
    }

    public ImageView getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(ImageView groupIcon) {
        this.groupIcon = groupIcon;
    }
}
