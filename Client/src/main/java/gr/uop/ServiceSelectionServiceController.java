package gr.uop;

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
        Service selectedService =  ServiceSelectionController.getServiceByController(this);
        
        // First check if service is already selected, if so then deselect
        if(Client.getCurrentOrder().containsService(selectedService)){
            this.deselect();
            return;
        }


        // Deselect all other unmatchable services first
        Iterator<Service> serviceIterator = ServiceSelectionController.getServices().iterator();
        while(serviceIterator.hasNext()){
            Service currentService = serviceIterator.next();
            System.out.println("#########################");
            System.out.println("currentServiceValues: " + currentService.getServiceValues());
            System.out.println("selectedServiceValues: " + selectedService.getServiceValues());
            if(currentService.containsValues(selectedService.getServiceValues())){
                ServiceSelectionController.getServiceController(currentService).deselect();
            }
            ServiceSelectionController.getServiceController(currentService);
        }


        System.out.println("Selecting " + this.serviceName.getText());
        Client.getCurrentOrder().addService(selectedService);

        // Style code for selecting
        this.selectButton.setStyle("-fx-background-color:  linear-gradient(to right, #47bb7c26, #4ACF9F26);-fx-background-radius:  0 0 11 11;-fx-text-fill: linear-gradient(to right, #47bb7c, #4ACF9F)");      
        this.selectButton.setText("Selected");
    }

    public void deselect(){
        // Get deselected service
        Service deselectedService =  ServiceSelectionController.getServiceByController(this);

        // Deselect service
        System.out.println("Deselecting " + this.serviceName.getText());
        Client.getCurrentOrder().removeService(deselectedService);

        // Style code for deselecting
        this.selectButton.setStyle("-fx-background-color:  linear-gradient(to right, #47bb7c, #4ACF9F);-fx-background-radius:  0 0 11 11;-fx-text-fill: white");      
        this.selectButton.setText("Select");
    }

    public void reselect(){
        // Deselect service
        System.out.println("Reselecting " + this.serviceName.getText());

        // Style code for selecting
        this.selectButton.setStyle("-fx-background-color:  linear-gradient(to right, #47bb7c26, #4ACF9F26);-fx-background-radius:  0 0 11 11;-fx-text-fill: linear-gradient(to right, #47bb7c, #4ACF9F)");      
        this.selectButton.setText("Selected");
    }
}
