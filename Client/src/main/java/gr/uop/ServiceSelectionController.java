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

public class ServiceSelectionController {


    
    @FXML
    private FlowPane serviceGroupHolder;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Button continueButton;
    
    @FXML
    public void initialize(){
        Client.getServiceObserver().addListener((ListChangeListener.Change<? extends Service> c) -> {
            Client.getCurrentOrder().clearServices();
            Iterator<Service> serviceIterator = Client.getServiceObserver().iterator();
            while(serviceIterator.hasNext()){
                Service currentService = serviceIterator.next();
                Client.getCurrentOrder().addService(currentService);
            }

            updateTotalCostLabel();
            if(Client.getServiceObserver().size() >= 1){
                continueButton.setDisable(false);
            }
            else{
                continueButton.setDisable(true);
            }
        });
    
        if(Client.getServiceObserver().size() >= 1){
            continueButton.setDisable(false);
        }
        else{
            continueButton.setDisable(true);
        }
    }

    
    
    public FlowPane getServiceGroupHolder() {
        return serviceGroupHolder;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceGroupHolder(FlowPane serviceGroupHolder) {
    //     this.serviceGroupHolder = serviceGroupHolder;
    // }

    public void addToServiceGroupHolder(Node serviceGroupHolder){
        this.serviceGroupHolder.getChildren().add(serviceGroupHolder);
    }

    public Label getTotalCostLabel() {
        return totalCostLabel;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setTotalCost(Label totalCost) {
    //     this.totalCost = totalCost;
    // }

    public void setTotalCostLabel(String totalCost) {
        getTotalCostLabel().setText(totalCost);
    }

    public void updateTotalCostLabel(){
        setTotalCostLabel("Total Cost: " + String.format("%.2f", Client.getCurrentOrder().getTotalCost())  + " €");
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

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        System.out.println("Going to Previous Page");
        Client.switchToVehicleTypePage();
        System.out.println("We are at Previous Page");
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        Client.switchToSelectedServiceListPage();
    }
    
}
