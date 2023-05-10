package JsonData;

/**
 * Class to import the location data from the json files.
 */
public class LocationData {

    /**
     * An array to hold the location objects.
     */
    private LocationObject[] data;

    /**
     * Creates the array to hold location objects.
     *
     * @param locations An array of locations.
     */
    public LocationData(LocationObject[] locations) {
        this.data = locations;
    }

    public LocationObject[] getData() {
        return data;
    }

    public void setData(LocationObject[] location) {
        this.data = location;
    }
}
