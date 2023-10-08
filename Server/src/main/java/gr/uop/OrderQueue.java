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

    public void addToOrderList(Order order) {
        getOrderList().add(order);
    }

    public void setOrderListFromFile() {
        // Initialize Lists
        ObservableList<Order> unfinishedOrders = FXCollections.observableArrayList();
        unfinishedOrders.add(new Order()); // Dummy order for proper tableView visual
        ObservableList<Order> allOrders = Server.readOrdersFromFile();

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
            addToOrderList(order);
            System.out.println(" ADDING ORDER: " + order.getRegistrationNumber());
        }
        System.out.println("Orders found in unfinishedOrders: " + unfinishedOrders.size());
        System.out.println("Orders found in allOrders: " + allOrders.size());
        System.out.println("Orders found in orderList: " + orderList.size());
    }
}
