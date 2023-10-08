package gr.uop;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

public class ServerController {
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
        // Get ArrayList of Orders
        ObservableList<Order> orderList = Server.readOrder();


        //Creating columns
        registrationNumberCol.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        entryTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDateTime"));

        
        //Adding data to the table
        orderHolder.setItems(orderList);
    }

    @FXML
    private void populateTable(){
       

    }

}
