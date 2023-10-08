package gr.uop;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order implements Serializable{

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

    public boolean containsService(Service service){
        return this.services.contains(service);
    }

    public void addService(Service service){
        this.services.add(service);
        this.updateTotalCost();
    }

    public void removeService(Service service){
        this.services.remove(service);
        this.updateTotalCost();
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void updateTotalCost(){
        double sum = 0;
        for(Service service: this.getServices()){
            sum += service.getServicePrice();
        }
        this.setTotalCost(sum);
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

    @Override
    public String toString(){
        return "Reg. Number: " + registrationNumber + " VehicleType: " 
        + vehicleType + " Services: " + services + " Total Cost: " + totalCost +
        " Arrival Date: " + arrivalDateTime + " Departure Date: " + departureDateTime;
    }

    
    public String toCSV(){
        String string = registrationNumber + ";" + vehicleType + ";";

        string += "[";
        for(Service service: services){
            string += service.getServiceName() + ":" + service.getServicePrice() + ",";
        }
        string += "]";

        string += ";" + totalCost + ";" + arrivalDateTime + ";" + departureDateTime;
        
        return string;
    }
}
