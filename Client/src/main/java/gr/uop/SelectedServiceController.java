package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    @FXML
    private Button removeButton;

    public Label getServiceName() {
        return serviceName;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceName(Label serviceName) {
    //     this.serviceName = serviceName;
    // }

    public void setServiceName(String serviceName) {
        this.getGroupName().setText(serviceName);
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

    public Label getGroupName() {
        return groupName;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setGroupName(Label groupName) {
    //     this.groupName = groupName;
    // }

    public void setGroupName(String groupName) {
        this.groupName.setText(groupName);
    }

    public ImageView getGroupIcon() {
        return groupIcon;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setGroupIcon(ImageView groupIcon) {
    //     this.groupIcon = groupIcon;
    // }

    public void setGroupIcon(String iconPath) {
        this.groupIcon.setImage(new Image(iconPath));
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(String removeButton) {
        this.removeButton.setText(removeButton);
    }
}
