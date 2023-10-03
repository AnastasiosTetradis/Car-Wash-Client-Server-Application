package gr.uop;

import java.util.ArrayList;

public class Vehicle {
    String vehicleName;
    String vehicleIcon;
    ArrayList<ServiceGroup> serviceGroupList = new ArrayList<>();

    public Vehicle(){

    }

    public Vehicle(String vehicleName, String vehicleIcon, ArrayList<ServiceGroup> serviceGroupList) {
        this.vehicleName = vehicleName;
        this.vehicleIcon = vehicleIcon;
        this.serviceGroupList = serviceGroupList;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleIcon() {
        return vehicleIcon;
    }

    public void setVehicleIcon(String vehicleIcon) {
        this.vehicleIcon = vehicleIcon;
    }

    public ArrayList<ServiceGroup> getServiceGroupList() {
        return serviceGroupList;
    }

    public void setServiceGroupList(ArrayList<ServiceGroup> serviceGroupList) {
        this.serviceGroupList = serviceGroupList;
    }

    public void addServiceGroup(ServiceGroup serviceGroup){
        this.serviceGroupList.add(serviceGroup);
    }

    public void addAllServiceGroups(ArrayList<ServiceGroup> serviceGroup){
        this.serviceGroupList.addAll(serviceGroup);
    }

    public String toString(){
        String string = vehicleName + "(" + vehicleIcon + ")\n";
        for(ServiceGroup serviceGroup: serviceGroupList){
            string += "\t" + serviceGroup.toString() + "\n";
        }
        return string;
    }
}
