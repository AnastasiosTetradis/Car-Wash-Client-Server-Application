package gr.uop;

import java.time.LocalDateTime;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

public class ServerController {

    ObservableList<Order> orderList = Server.getOrderQueue().getOrderList();

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
        orderHolder.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            proceedButton.setDisable(false);
            cancelButton.setDisable(false);
            // updateServiceHolder();
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
}
