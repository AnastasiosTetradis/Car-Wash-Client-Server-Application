package gr.uop;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServiceSelectionController {

    private static ObservableList<Service> serviceObserver = FXCollections.observableArrayList();

    private static LinkedHashMap<Service, ServiceSelectionServiceController> serviceMap = new LinkedHashMap<>();
    
    @FXML
    private FlowPane serviceGroupHolder;

    @FXML
    private Label totalCostLabel;

    @FXML
    private Button continueButton;
    
    @FXML
    public void initialize(){
        serviceObserver.addListener((ListChangeListener.Change<? extends Service> c) -> {
            updateTotalCostLabel();
            if(serviceObserver.size() >= 1){
                continueButton.setDisable(false);
            }
            else{
                continueButton.setDisable(true);
            }
        });
    }

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

    public static LinkedHashMap<Service, ServiceSelectionServiceController> getServiceMap(){
        return serviceMap;
    }

    public static Set<Service> getServices(){
        return serviceMap.keySet();
    }

    public static Service getServiceByNameAndGroup(String serviceName, ServiceGroup serviceGroup){
        Iterator<Service> iterator = getServices().iterator();
        while(iterator.hasNext()){
            Service currentService = iterator.next();
            if(currentService.getServiceName().equals(serviceName) && currentService.getServiceGroup().equals(serviceGroup)){
                return currentService;
            }
        }
        return null;
    }

    public static Service getServiceByController(ServiceSelectionServiceController controller){
        Iterator<Service> iterator = getServices().iterator();
        while(iterator.hasNext()){
            Service currentService = iterator.next();
            ServiceSelectionServiceController currentController = getServiceController(currentService);
            if(currentController == controller){
                return currentService;
            }
        }
        return null;
    }

    public static ServiceSelectionServiceController getServiceController(Service service){
        return serviceMap.get(service);
    }

    public static void addService(Service service, ServiceSelectionServiceController controller){
        serviceMap.put(service, controller);
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
