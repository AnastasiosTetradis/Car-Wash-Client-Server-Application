package gr.uop;

import java.io.Serializable;
import java.util.ArrayList;

public class ServiceGroup implements Serializable{
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
        if (obj instanceof ServiceGroup == false)  {
            return false;
        }
        /*
        if (obj.getClass() != this.getClass())  {
            return false;
        }
        */
        
        // Μετατροπή τύπου από Object
        ServiceGroup objServiceGroup = (ServiceGroup)obj;
        
        // Τελικά, υλοποίηση του ελέγχου ισότητας των πεδίων που ενδιαφέρουν
        if (this.groupName.equals(objServiceGroup.groupName))  {
            return true;
        }
        else  {
            return false;
        }
    }
}