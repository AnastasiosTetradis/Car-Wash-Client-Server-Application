package gr.uop;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class ServiceDB {
    private final static String RED = "\u001B[31m";
    private final static String GREEN = "\u001B[32m";
    private final static String BLUE = "\u001B[34m";
    private final static String YELLOW = "\u001B[33m";
    private final static String DEFAULT = "\u001b[0m";

    // Source: https://stackoverflow.com/questions/65702840/linkedhashmap-vs-linkedhashset-for-retrieving-specific-elements-retrieving-in?noredirect=1&lq=1
    private LinkedHashMap<Vehicle, Vehicle> data = new LinkedHashMap<>();

    
    public ServiceDB(){
        
    }

    public LinkedHashMap<Vehicle, Vehicle> getData() {
        return data;
    }

    public void setData(LinkedHashMap<Vehicle, Vehicle> data) {
        this.data = data;
    }

    public void clearData(){
        this.data.clear();
    }
    
    public void getFromData(Vehicle vehicle){
        this.data.get(vehicle);
    }

    public void addToData(Vehicle vehicle){
        this.data.put(vehicle, vehicle);
    }

    public Set<Vehicle> getAllVehicles(){
        return data.keySet();
    }

    public Vehicle getVehicleByName(String vehicleName){
        Iterator<Vehicle> iterator = this.getAllVehicles().iterator();
        while(iterator.hasNext()){
            Vehicle vehicle = iterator.next();

            if(vehicle.getVehicleName().equals(vehicleName)){
                return vehicle;
            }
        }
        return null;
    }

    public int getVehicleCount(){
        return data.keySet().size();
    }

    public Set<ServiceGroup> getAllServiceGroups(){
        Set<ServiceGroup> groupSet = new TreeSet<>();

        Iterator<Vehicle> vehicleIterator = this.getAllVehicles().iterator();
        while(vehicleIterator.hasNext()){
            Iterator<ServiceGroup> groupIterator = vehicleIterator.next().getServiceGroupList().iterator();
            while(groupIterator.hasNext()){
                groupSet.add(groupIterator.next());
            }
        }

        return groupSet;
    }

    public int getServiceGroupCount(){
        return this.getAllServiceGroups().size();
    }

    public Set<Service> getAllServices(){
        Set<Service> serviceSet = new TreeSet<>();
        Iterator<ServiceGroup> groupIterator = this.getAllServiceGroups().iterator();
        while(groupIterator.hasNext()){
            Iterator<Service> serviceIterator = groupIterator.next().getServices().iterator();
            while(serviceIterator.hasNext()){
                serviceSet.add(serviceIterator.next());
            }
        }

        return serviceSet;
    }

    public int getServiceCount(){
        return this.getAllServices().size();
    }

    public void setFromFile(File file){
        this.clearData();
        this.addFromFile(file);
    }

    public void addFromFile(File file){

        String inputString = "";

        // Finding Config File
        try{
            Scanner scanner = new Scanner(file);

            //Storing all data from file into a string 
            while(scanner.hasNextLine()){
                inputString += scanner.nextLine();
            }

            scanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File could not be found! Please try again.");
            return;
        }



        // Get <Vehicles> tag content
        String vehiclesTag[] = getTagContent(inputString, "vehicles");

        // Get all <Vehicle> tags' content
        System.out.println(YELLOW + "INSTANCE OF vehicleTag[1]:" + BLUE + "|" + RED + vehiclesTag[0] + BLUE + "|" + DEFAULT);
        String vehicleTags[] = getTagContent(vehiclesTag[0], "vehicle"); // Important detail hidden here (vehiclesTag[1])!!!
        
        for(String vehicleTag: vehicleTags){
            
            // Reading each <Vehicle> tag's content
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleName(getTagContent(vehicleTag, "vehicleName")[0]);
            vehicle.setVehicleIcon(getTagContent(vehicleTag, "vehicleIcon")[0]);
            ArrayList<Service> serviceList = new ArrayList<>();

            // Get <serviceList> tag's content
            String serviceListTags = getTagContent(vehicleTag, "serviceList")[0];

            // Get all <serviceGroup> tags' content
            String[] serviceGroupTags = getTagContent(serviceListTags, "serviceGroup");
            for(String serviceGroupTag: serviceGroupTags){
                ServiceGroup serviceGroup = new ServiceGroup();
                serviceGroup.setGroupName(getTagContent(serviceGroupTag, "groupName")[0]);
                serviceGroup.setGroupIcon(getTagContent(serviceGroupTag, "groupIcon")[0]);

                // Code for groupValues here


                // Code for groupValues here

                ArrayList<Service> services = new ArrayList<>();
                
                // Get <services> tag's content
                String servicesTags = getTagContent(serviceGroupTag, "services")[0];

                // Get <service> tag's content
                String[] serviceTags = getTagContent(servicesTags, "service");
                for(String serviceTag: serviceTags){
                    System.out.println(YELLOW + "DEBUG: current Input Scope: " + BLUE + serviceTag);

                    // Reading each <service> tag's content
                    Service service = new Service();
                    service.setServiceName(getTagContent(serviceTag, "serviceName")[0]);
                    service.setServicePrice(Double.parseDouble(getTagContent(serviceTag, "servicePrice")[0]));
                    String valueStrings[] = getTagContent(serviceTag, "serviceValues")[0].split(",");

                    for(String valueString: valueStrings){
                        service.addServiceValue(Integer.parseInt(valueString));
                    }

                    service.setServiceGroup(serviceGroup);

                    serviceGroup.addService(service);
                    
                    // Storing each <service> tag's content
                    serviceList.add(service);
                }


                vehicle.addServiceGroup(serviceGroup);
            }
            

            // Storing each <vehicle> tag's content in serviceDB
            this.addToData(vehicle);
        }
    }

    public String[] getTagContent(String string, String tag){
        String tagContents[] = string.split("<" + tag + ">");
        tagContents = Arrays.copyOfRange(tagContents, 1, tagContents.length);

        System.out.println(GREEN + "DEBUG: searchTag: " + BLUE + tag + DEFAULT);
        System.out.println(GREEN + "DEBUG: current Input Scope: " + BLUE + string);

        for(int i = 0; i < tagContents.length; i++){
            try{
                tagContents[i] = tagContents[i].trim();
                System.out.println(GREEN + "DEBUG: uncut tag: " + BLUE + tagContents[i] + DEFAULT);
                if(!tagContents[i].equals("")){
                    tagContents[i] = tagContents[i].substring(0, tagContents[i].indexOf("</" + tag + ">")).trim();
                    System.out.println(GREEN + "DEBUG: cut tag: " + BLUE + tagContents[i] + DEFAULT);
                }
            }
            catch(StringIndexOutOfBoundsException e){
                throw new InputMismatchException();
            }
        }
        System.out.println(GREEN + "contents of " + tag + "Tag: " + RED + Arrays.toString(tagContents) + DEFAULT);
        return tagContents;
    }

    public String toString(){
        String string = "";
        
        Iterator<Vehicle> iterator = this.getAllVehicles().iterator();

        string += this.getVehicleCount() + " Vehicle(s)\n\n";

        while(iterator.hasNext()){
            Vehicle vehicle = iterator.next();
            string += vehicle.toString() + "\n";
        }

        return string;
    }
}
