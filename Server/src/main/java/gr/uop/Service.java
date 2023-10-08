package gr.uop;

import java.io.Serializable;
import java.util.ArrayList;

public class Service implements Serializable{
    private String serviceName;
    private double servicePrice;
    private ArrayList<Integer> serviceValues = new ArrayList<>();
    private ServiceGroup serviceGroup;

    public Service(){
        serviceName = "";
        servicePrice = 0;
    }

    public Service(String serviceName, double servicePrice, ServiceGroup serviceGroup){
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceGroup = serviceGroup;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public ArrayList<Integer> getServiceValues() {
        return serviceValues;
    }

    public void setServiceValues(ArrayList<Integer> serviceValues) {
        this.serviceValues = serviceValues;
    }

    public boolean containsValues(ArrayList<Integer> serviceValues){
        for(Integer serviceValue: serviceValues){
            if(this.serviceValues.contains(serviceValue)){
                return true;
            }
        }
        return false;
    }

    public void addServiceValue(Integer value) {
        this.serviceValues.add(value);
    }

    public void addAllServiceValues(ArrayList<Integer> serviceValues) {
        for(Integer serviceValue: serviceValues){
            this.addServiceValue(serviceValue);
        }
    }
    
    public ServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public String toString(){
        return serviceName + " " + servicePrice + " groudID's: " + serviceValues;
    }
}
