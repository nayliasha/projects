import java.io.*;
import java.util.*;

//class that behaves like a map
public class LocationMap implements Map<Integer, Location> {

    private static final String LOCATIONS_FILE_NAME =  "locations.txt";
    private static final String DIRECTIONS_FILE_NAME =  "directions.txt";

    //create a static locations HashMap
    static HashMap<Integer, Location> locations = new HashMap<>();

    static {
        //create a FileLogger object
        FileLogger fileLog = new FileLogger();

        //create a ConsoleLogger object
        ConsoleLogger consoleLog = new ConsoleLogger();

        /**
         * Read from LOCATIONS_FILE_NAME so that a user can navigate from one location to another
         * use try-with-resources/catch block for the FileReader
         * extract the location and the description on each line
         * print all locations and descriptions to both console and file
         * check the ExpectedOutput files
         * put the location and a new Location object in the locations HashMap, using temporary empty hashmaps for exits
         */
        //buffered reader used to read the text file
        //while next line in file exits, add line to array
        try (BufferedReader br = new BufferedReader(new FileReader(LOCATIONS_FILE_NAME))) {
            String line;
            ArrayList<String> locationsFile = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                //line.split(",");
                locationsFile.add(line);
            }

            //print message to file and console
            fileLog.log("Available locations:"); //print to file
            consoleLog.log("Available locations:"); //print to console

            for (String locationId : locationsFile) {
                String locationNum = locationId.split(",")[0]; //split by comma and take element at index 0, i.e. before the comma
                int locationNumber = Integer.parseInt(locationNum); //convert from string to int and store in int variable called location number

                //save description in a variable called location description -> substring after comma
                String locationDescription = locationId.substring(locationId.indexOf(",") + 1);

                fileLog.log(locationNumber + ": " + locationDescription); //print to file
                consoleLog.log(locationNumber + ": " + locationDescription); //print to console

                //put each location in the locations<Integer, Location> hashmap (using a temporary empty hashmap)
                //remember Location(int locationId, String description, Map<String, Integer> exits)
                locations.put(locationNumber, new Location(locationNumber, locationDescription, new LinkedHashMap<>()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * Read from DIRECTIONS_FILE_NAME so that a user can move from A to B, i.e. current location to next location
         * use try-with-resources/catch block for the FileReader
         * extract the 3 elements  on each line: location, direction, destination
         * print all locations, directions and destinations to both console and file
         * check the ExpectedOutput files
         * add the exits for each location
         */
        try (BufferedReader br = new BufferedReader(new FileReader(DIRECTIONS_FILE_NAME))) {
            String line;
            ArrayList<String> directionsFile = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                //line.split(",");
                directionsFile.add(line);
            }

            fileLog.log("Available directions:"); //print to file
            consoleLog.log("Available directions:"); //print to console

            //create arraylists to hold the values from the directions file - outside for loop
            ArrayList<Integer> locationsArray = new ArrayList<Integer>();
            ArrayList<String> directionsArray = new ArrayList<String>();
            ArrayList<Integer> destinationsArray = new ArrayList<Integer>();

            for (String directionId : directionsFile) { //for each entry in the directions File array

                String locationNum = directionId.split(",")[0]; //split by comma and take element at index 0, i.e. before the comma
                int locationNumber = Integer.parseInt(locationNum); //convert from string to int and store in int variable called location number
                locationsArray.add(locationNumber); //add each location number to the locations array list

                // tried various methods, used this one
                //method 3 (for direction)
                String[] directions = directionId.split(","); //split based on comma
                String direction = directions[1]; //take element at index 1 and store in string variable called direction
                directionsArray.add(direction); //add each direction to directions array list

                //split sentence by comma and take substring after the last appearing comma
                String destinationNum = directionId.substring(directionId.lastIndexOf(",") + 1);
                int destinationNumber = Integer.parseInt(destinationNum); //convert from string to int and store in int variable called destination number
                destinationsArray.add(destinationNumber); //add each destination number to the destinations array list

                fileLog.log(locationNumber + ": " + direction + ": " + destinationNumber); //print to file log
                consoleLog.log(locationNumber + ": " + direction + ": " + destinationNumber); //print to console log
            }

            for (HashMap.Entry<Integer, Location> hashMap : locations.entrySet()) {//for each entry in locations hashmap
                for (int i = 0; i < locationsArray.size(); i++) //iterate through the locations array and
                    if (hashMap.getKey().equals(locationsArray.get(i))) { //if the key equals the location
                        hashMap.getValue().addExit(directionsArray.get(i), destinationsArray.get(i));
                        //add the exits (string direction, int destination) for each location
                    }
            }

        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

    }

    //implement all methods for Map
    // @return
    //HashMap<Integer, Location> locations = new HashMap<>();
    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {
        locations.putAll(m);
    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
