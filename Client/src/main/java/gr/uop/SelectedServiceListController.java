package gr.uop;

import java.io.IOException;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SelectedServiceListController {

        @FXML
    private FlowPane serviceHolder;

    @FXML
    private Label totalCost;

    public FlowPane getServiceGroupHolder() {
        return serviceHolder;
    }

    // https://stackoverflow.com/questions/29500761/javafx-change-the-image-in-an-imageview
    // public void setServiceGroupHolder(FlowPane serviceHolder) {
    //     this.serviceHolder = serviceHolder;
    // }

    public void addToServiceHolder(Node serviceHolder){
        this.serviceHolder.getChildren().add(serviceHolder);
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

    @FXML
    public void switchToPreviousScene(ActionEvent event) throws IOException{
        // Get ServiceSelection FXML
        FXMLLoader serviceSelectionLoader = new FXMLLoader(getClass().getResource("ServiceSelection.fxml"));
        Parent root = serviceSelectionLoader.load();
        ServiceSelectionController serviceSelectionController = serviceSelectionLoader.getController();

        // For every serviceGroup
        String selectedVehicleType = Client.getCurrentOrder().getVehicleType();
        Iterator<ServiceGroup> serviceGroupIterator = Client.getDb().getVehicleByName(selectedVehicleType).getServiceGroupList().iterator();
        while(serviceGroupIterator.hasNext()){
            // Get current Service Group
            ServiceGroup currentGroup = serviceGroupIterator.next();

            // Get current Service Group FXML
            FXMLLoader serviceGroupLoader = new FXMLLoader(getClass().getResource("servicegroup_frame.fxml")); 
            VBox groupVbox = serviceGroupLoader.load();
            ServiceSelectionGroupController serviceGroupController = serviceGroupLoader.getController();

            // Set up current Service Group FXML
            serviceGroupController.setGroupName(currentGroup.getGroupName());
            String iconName = currentGroup.getGroupIcon().strip();
            if(!iconName.equals("")){
                System.out.println("IconName=" + iconName);
                serviceGroupController.setGroupIcon(getClass().getResource("data/" + iconName).toString());
            }

            // Get all current group's services
            Iterator<Service> serviceIterator = currentGroup.getServices().iterator();
            while(serviceIterator.hasNext()){
                // Get current service
                Service currentService = serviceIterator.next();

                // Get current service FXML
                FXMLLoader serviceLoader = new FXMLLoader(getClass().getResource("service_button.fxml")); 
                VBox serviceVbox = serviceLoader.load();
                ServiceSelectionServiceController currentServiceController = serviceLoader.getController();

                // Set up current service FXML
                currentServiceController.setServiceName(currentService.getServiceName());
                currentServiceController.setServicePrice(currentService.getServicePrice() + " €");
                
                // Add service FXML to ServiceGroup FXML
                serviceGroupController.addToServiceHolder(serviceVbox);

                
                // Add service with its controller to serviceMap
                ServiceSelectionController.addService(currentService, currentServiceController);
                System.out.println("In serviceMap: " + currentService);
            }

            // Add ServiceGroup FXML to ServiceSelection FXML
            serviceSelectionController.addToServiceGroupHolder(groupVbox);
            serviceSelectionController.updateTotalCostLabel();
        }

        // Reselect all already selected services 
        Iterator<Service> serviceIterator = ServiceSelectionController.getServices().iterator();
        while(serviceIterator.hasNext()){
            Service currentService = serviceIterator.next();
            if(Client.getCurrentOrder().containsService(currentService)){
                ServiceSelectionController.getServiceController(currentService).reselect();
            }
        }

        System.out.println("FlowPanes children: " + serviceSelectionController.getServiceGroupHolder().getChildren().size());

        // root = vehicleTypeLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToNextScene(ActionEvent event) throws IOException{
        
    }
}
