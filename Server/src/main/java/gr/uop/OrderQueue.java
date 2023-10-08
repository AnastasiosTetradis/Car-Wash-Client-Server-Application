package gr.uop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderQueue {
    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    public ObservableList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ObservableList<Order> orderList) {
        this.orderList = orderList;
    }

    public void setOrderListFromFile() {
        // Initialize Lists
        ObservableList<Order> unfinishedOrders = FXCollections.observableArrayList();
        ObservableList<Order> allOrders = Server.readOrderFromFile();

        // For every order in profit file
        for(Order order :allOrders){
            if(order.getDepartureDateTime() == null){
                // If unfinished, add order to list
                unfinishedOrders.add(order);
            }
        }

        // Fill orderList with unfinishedOrders
        this.orderList.clear();
        for(Order order: unfinishedOrders){
            this.orderList.add(order);
        }

    }
}
