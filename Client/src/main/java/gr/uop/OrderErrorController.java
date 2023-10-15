package gr.uop;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OrderErrorController {
    @FXML
    private Button backButton;

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(String backButton) {
        this.backButton.setText(backButton);
    }

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        Client.switchToSelectedServiceListPage();
    }
}
