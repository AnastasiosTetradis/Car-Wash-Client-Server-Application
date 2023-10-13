package gr.uop;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ServiceSelectionServiceController {
    
    @FXML
    private Label serviceName;

    @FXML
    private Label servicePrice;

    @FXML
    private Button selectButton;

    public Label getServiceName() {
        return serviceName;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceName(Label serviceName) {
    //     this.serviceName = serviceName;
    // }

    public void setServiceName(String serviceName) {
        this.serviceName.setText(serviceName);
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

    public Button getSelectButton() {
        return selectButton;
    }

    public void setSelectButton(String selectButton) {
        this.selectButton.setText(selectButton);
    }

    public void select(){
        // Get selected service
        Service selectedService =  Client.getServiceByControllerFromServiceSelectionMap(this);
        
        // First check if service is already selected, if so then deselect
        if(Client.getServiceObserver().contains(selectedService)){
            this.deselect();
            return;
        }


        // Deselect all other unmatchable services first
        ArrayList<Service> serviceMemory = new ArrayList<>();
        serviceMemory.addAll(Client.getServiceObserver());
        Iterator<Service> serviceIterator = serviceMemory.iterator();
        while(serviceIterator.hasNext()){
            Service currentService = serviceIterator.next();
            System.out.println("#########################");
            System.out.println("currentServiceValues: " + currentService.getServiceValues());
            System.out.println("selectedServiceValues: " + selectedService.getServiceValues());
            if(currentService.containsValues(selectedService.getServiceValues())){
                Client.getServiceControllerFromServiceSelectionMap(currentService).deselect();
            }
            Client.getServiceControllerFromServiceSelectionMap(currentService);
        }

        // Selecting Service
        Client.addToServiceObserver(selectedService);
        
        // Style code for selecting
        coloringButton(true);
    }

    public void deselect(){
        // Get deselected service
        Service deselectedService =  Client.getServiceByControllerFromServiceSelectionMap(this);

        // Deselect service
        System.out.println("Deselecting " + this.serviceName.getText());
        Client.removeFromServiceObserver(deselectedService);


        // Style code for deselecting
        coloringButton(false);
    }

    // Style code for selecting
    public void coloringButton(boolean activatingButton){
        if(activatingButton) {
            System.out.println("Coloring " + this.serviceName.getText());
            this.selectButton.setStyle("-fx-background-color:  linear-gradient(to right, #47bb7c26, #4ACF9F26);-fx-background-radius:  0 0 11 11;-fx-text-fill: linear-gradient(to right, #47bb7c, #4ACF9F)");      
            this.selectButton.setText("Selected");
        }
        else {
            System.out.println("Decoloring " + this.serviceName.getText());
            this.selectButton.setStyle("-fx-background-color:  linear-gradient(to right, #47bb7c, #4ACF9F);-fx-background-radius:  0 0 11 11;-fx-text-fill: white");      
            this.selectButton.setText("Select");
        }
    }
}
