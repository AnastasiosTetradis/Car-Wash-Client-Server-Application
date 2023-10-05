package gr.uop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ServiceSelectionGroupController {
    
    @FXML
    private Label groupName;

    @FXML
    private ImageView groupIcon; 

    @FXML
    private FlowPane groupHolder;

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

    public FlowPane getGroupHolder() {
        return groupHolder;
    }

    public void setGroupHolder(FlowPane groupHolder) {
        this.groupHolder = groupHolder;
    }

}
