package gr.uop;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

public class ServerController {

    private ObservableList<Order> orderList = Server.getOrderQueue().getOrderList();

    @FXML
    private TableView<Order> orderHolder;

    @FXML
    private FlowPane serviceHolder;

    @FXML
    private Button proceedButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableColumn<Order, String> registrationNumberCol;

    @FXML
    private TableColumn<Order, String> vehicleCol;
    
    @FXML
    private TableColumn<Order, Double> costCol;

    @FXML
    private TableColumn<Order, LocalDateTime> entryTimeCol;


    @FXML
    public void initialize(){

        // https://itecnote.com/tecnote/java-how-to-write-a-new-listchangelistener-with-lambda/
        orderList.addListener((ListChangeListener.Change<? extends Order> c) -> {
            populateOrderHolder();
        });

        // https://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
        orderHolder.setOnMouseClicked(event -> {
            // Make sure the user clicked on a populated item
            Order selectedOrder = orderHolder.getSelectionModel().getSelectedItem();
            if(selectedOrder != null){

                proceedButton.setDisable(false);
                cancelButton.setDisable(false);
                updateServiceHolder(selectedOrder);
            }
            else{
                proceedButton.setDisable(true);
                cancelButton.setDisable(true);
                clearServiceHolder();
            }
        });

        orderHolder.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            int index = orderHolder.getSelectionModel().getSelectedIndex();
            if(index < 0){
                proceedButton.setDisable(false);
                cancelButton.setDisable(false);
            }
            else{
                proceedButton.setDisable(true);
                cancelButton.setDisable(true);
            }
        }); 
    }

    @FXML
    private void populateOrderHolder(){
       
        //Creating columns
        registrationNumberCol.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        entryTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDateTime"));

        //Adding data to the table
        orderHolder.setItems(orderList);
    }

    @FXML
    public void proceedOrderShortcut(KeyEvent e){
        // https://stackoverflow.com/questions/13880638/how-do-i-pick-up-the-enter-key-being-pressed-in-javafx2
        if(e.getCode() == KeyCode.ENTER)
        {
            proceedOrder();
        }
    }

    @FXML
    public void proceedOrder(){
        Order selectedOrder = orderHolder.getSelectionModel().getSelectedItem();
        selectedOrder.setDepartureDateTime(LocalDateTime.now());
        System.out.println(LocalDateTime.now());
        Server.completeOrderInFile(selectedOrder);
        clearServiceHolder();
    }

    @FXML
    public void cancelOrder(){
        Order selectedOrder = orderHolder.getSelectionModel().getSelectedItem();
        Server.deleteOrderInFile(selectedOrder);
        clearServiceHolder();
    }

    public void updateServiceHolder(Order selectedOrder){
        ArrayList<Service> services =  selectedOrder.getServices();
        serviceHolder.getChildren().clear();

        // For every Service in selectedOrder
        for(Service service :services){
            // Get Service FXML
            FXMLLoader serviceFrameLoader = new FXMLLoader(getClass().getResource("selectedservice_frame.fxml"));
            Parent serviceRoot = null;
            try{
                serviceRoot = serviceFrameLoader.load();
            }
            catch(IOException e){
                System.out.println("FXML file doesn't exist. Please find it.");
            }

            // Get frame controller
            ServiceFrameController serviceFrameController = serviceFrameLoader.getController();

            // Set service group name in frame
            serviceFrameController.setServiceGroupLabel(service.getServiceGroup().getGroupName());

            // Set service name in frame
            serviceFrameController.setServiceNameLabel(service.getServiceName());

            // Set service price in frame
            serviceFrameController.setServicePriceLabel(service.getServicePrice() + " €");

            // Add service pane to serviceHolder
            serviceHolder.getChildren().add(serviceRoot);
        }
    }

    public void clearServiceHolder(){
        serviceHolder.getChildren().clear();
    }
}
