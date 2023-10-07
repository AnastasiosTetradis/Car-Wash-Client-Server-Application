package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ServiceSelectionGroupController {
    
    @FXML
    private Label groupName;

    @FXML
    private ImageView groupIcon; 

    @FXML
    private FlowPane serviceHolder;

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

    public FlowPane getServiceHolder() {
        return serviceHolder;
    }

    public void addToServiceHolder(Node serviceHolder){
        this.serviceHolder.getChildren().add(serviceHolder);
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceHolder(FlowPane serviceHolder) {
    //     this.serviceHolder = serviceHolder;
    // }

}
