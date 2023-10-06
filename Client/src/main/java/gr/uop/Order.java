package gr.uop;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private String registrationNumber = "";
    private String vehicleType = "";
    private ArrayList<Service> services = new ArrayList<>();
    private double totalCost = 0;
    private LocalDateTime arrivalDateTime;
    private LocalDateTime departureDateTime;

    public Order(){

    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    public ArrayList<Service> getServices() {
        return services;
    }
    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }
}
