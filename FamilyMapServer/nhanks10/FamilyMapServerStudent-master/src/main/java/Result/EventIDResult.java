package Result;

/**
 * Makes a result when a single event is requested from the database.
 */
public class EventIDResult extends GenericResult {

    /**
     * String, not null, unique.
     */
    private String eventID;

    /**
     * String, not null.
     */
    private String associatedUsername;

    /**
     * String, not null.
     */
    private String personID;

    /**
     * Float, not null.
     */
    private float latitude;

    /**
     * Float, not null.
     */
    private float longitude;

    /**
     * String, not null.
     */
    private String country;

    /**
     * String, not null.
     */
    private String city;

    /**
     * String, not null.
     */
    private String eventType;

    /**
     * Int, not null.
     */
    private int year;

    /**
     * Makes a result message for a successful event creation.
     *
     * @param message            String message
     * @param success            Boolean success
     * @param eventID            String, unique, of the eventID.
     * @param associatedUsername String of the username associated with the event.
     * @param personID           String related to the ID of the person involved.
     * @param latitude           float of the latitude.
     * @param longitude          float of the longitude.
     * @param country            String of the country the event is in.
     * @param city               String of the city the event is in.
     * @param eventType          String of the type of event such as baptism, marriage, death etc.
     * @param year               int of when the event happened.
     */
    public EventIDResult(String message, boolean success, String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Makes a result error when the request fails and sends a message.
     *
     * @param message error message
     */
    public EventIDResult(String message) {
        super(message, false);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
