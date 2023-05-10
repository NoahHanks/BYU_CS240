package JsonData;

/**
 * Class used to create location objects from json files.
 */
public class LocationObject {
    /**
     * String country
     */
    private String country;

    /**
     * String city
     */
    private String city;

    /**
     * String latitude
     */
    private float latitude;

    /**
     * String longitude
     */
    private float longitude;

    /**
     * Creates a location object from data taken from the json files.
     *
     * @param country   String country
     * @param city      String city
     * @param latitude  String latitude
     * @param longitude String longitude
     */
    public LocationObject(String country, String city, float latitude, float longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
