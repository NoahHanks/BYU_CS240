package Model;

import java.util.Objects;

/**
 * Class for the model of Family Map events.
 */
public class Event {

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
     * Creates an Event model with an event id, associated username, person id, latitude, longitude, country, city, event type, and year.
     *
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
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    /**
     * Overrides the equals() method.
     *
     * @param o Event Object
     * @return True if objects are equal. False if objects aren't equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.latitude, latitude) == 0
                && Float.compare(event.longitude, longitude) == 0
                && year == event.year && Objects.equals(eventID, event.eventID)
                && Objects.equals(associatedUsername, event.associatedUsername)
                && Objects.equals(personID, event.personID)
                && Objects.equals(country, event.country)
                && Objects.equals(city, event.city)
                && Objects.equals(eventType, event.eventType);
    }
}
