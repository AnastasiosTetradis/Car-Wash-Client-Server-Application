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

    @Override
    public boolean equals(Object obj)
    {
        // Διαγνωστική εκτύπωση
        //System.out.println("in equals");
        
        // Έλεγχος για null
        if (obj == null)  {
            return false;
        }
        
        // Προαιρετικά, έλεγχος αν πρόκειται για το ίδιο ακριβώς αντικείμενο
        if (this == obj)  {
            return true;
        }
        
        // Έλεγχος αν ταιριάζουν οι κλάσεις
        // (Μπορεί να χρησιμοποιηθεί και η getClass(), ανάλογα με τις ανάγκες
        // της υλοποίησης)
        if (obj instanceof Vehicle == false)  {
            return false;
        }
        /*
        if (obj.getClass() != this.getClass())  {
            return false;
        }
        */
        
        // Μετατροπή τύπου από Object
        Vehicle objVehicle = (Vehicle)obj;
        
        // Τελικά, υλοποίηση του ελέγχου ισότητας των πεδίων που ενδιαφέρουν
        if (this.vehicleName.equals(objVehicle.vehicleName))  {
            return true;
        }
        else  {
            return false;
        }
    }
}
