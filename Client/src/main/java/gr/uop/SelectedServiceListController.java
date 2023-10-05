package gr.uop;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class SelectedServiceListController {

        @FXML
    private FlowPane serviceHolder;

    @FXML
    private Label totalCost;

    public FlowPane getServiceGroupHolder() {
        return serviceHolder;
    }

    public void setServiceGroupHolder(FlowPane serviceHolder) {
        this.serviceHolder = serviceHolder;
    }

    public Label getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Label totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost.setText(totalCost);
    }

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("ServiceSelection.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        
    }
}
