package gr.uop;

import java.io.IOException;
import java.util.Iterator;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class SelectedServiceListController {

    // private static ObservableList<Service> serviceObserver = FXCollections.observableArrayList();

    @FXML
    private FlowPane serviceHolder;

    @FXML
    private Label totalCost;

    @FXML
    private Button continueButton;

    @FXML
    public void initialize() {
        Client.getServiceObserver().addListener((ListChangeListener.Change<? extends Service> c) -> {
            Client.getCurrentOrder().clearServices();
            Iterator<Service> serviceIterator = Client.getServiceObserver().iterator();
            while(serviceIterator.hasNext()){
                Service currentService = serviceIterator.next();
                Client.getCurrentOrder().addService(currentService);
            }

            updateTotalCost();
            updateServiceHolder();
            if(Client.getServiceObserver().size() >= 1){
                continueButton.setDisable(false);
            }
            else{
                continueButton.setDisable(true);
            }
        });
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

    public void updateServiceHolder() {
        try {
            serviceHolder.getChildren().clear();
            Client.generateServiceHolder(this);
        }
        catch(IOException e) {
            System.out.println("ERROR: Selected Service Page could not be loaded.");
        }
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

    public void updateTotalCost(){
        setTotalCost("Total Cost: " + String.format("%.2f", Client.getCurrentOrder().getTotalCost())  + " €");
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
        Client.sendData();
    }
}
