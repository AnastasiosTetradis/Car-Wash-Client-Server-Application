package gr.uop;

import java.util.ArrayList;

public class ServiceGroup{
    private String groupName;
    private String groupIcon;
    private ArrayList<Service> services = new ArrayList<>();

    public ServiceGroup(){

    }

    public ServiceGroup(String groupName, String groupIcon){
        this.groupName = groupName;
        this.groupIcon = groupIcon;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services.clear();
        this.services.addAll(services);
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public void addAllServices(ArrayList<Service> services) {
        this.services.addAll(services);
    }

    public String toString(){
        String string = groupName + "(" + groupIcon + ")\n";
        for(Service service: services){
            string += "\t" + service.toString() + "\n";
        }
        return string;
    }
}