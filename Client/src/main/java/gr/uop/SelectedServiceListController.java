package gr.uop;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelectedServiceListController {

    private static ObservableList<Service> serviceObserver = FXCollections.observableArrayList();

    @FXML
    private FlowPane serviceHolder;

    @FXML
    private Label totalCost;

    @FXML
    private Button continueButton;

    public static ObservableList<Service> getServiceObserver() {
        return serviceObserver;
    }

    public static void setServiceObserver(ObservableList<Service> serviceObserverList) {
        serviceObserver = serviceObserverList;
    }

    public static void setServiceObserver(Service service) {
        serviceObserver.set(0, service);
    }

    public static void addToServiceObserver(Service service){
        serviceObserver.add(service);
    }

    public static void removeFromServiceObserver(Service service){
        serviceObserver.remove(service);
    }

    public FlowPane getServiceHolder() {
        return serviceHolder;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceGroupHolder(FlowPane serviceHolder) {
    //     this.serviceHolder = serviceHolder;
    // }

    public void addToServiceHolder(Node serviceHolder){
        this.serviceHolder.getChildren().add(serviceHolder);
    }

    public void removeFromServiceHolder(Node serviceHolder){
        this.serviceHolder.getChildren().remove(serviceHolder);
    }

    public Label getTotalCost() {
        return totalCost;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setTotalCost(Label totalCost) {
    //     this.totalCost = totalCost;
    // }

    public void setTotalCost(String totalCost) {
        this.totalCost.setText(totalCost);
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

    // @FXML
    // public void initialize(){
    //     serviceObserver.addListener((ListChangeListener.Change<? extends Service> c) -> {
    //         updateTotalCost();
    //         if(serviceObserver.size() >= 1){
    //             continueButton.setDisable(false);
    //         }
    //         else{
    //             continueButton.setDisable(true);
    //         }
    //     });
    // }

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        Client.switchToServiceSelectionPage();
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        Client.switchToThankYouPage();
    }
}
