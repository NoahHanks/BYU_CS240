package Generator;

import JsonData.FileReader;
import JsonData.LocationData;
import JsonData.LocationObject;
import Model.Event;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Generates events for the family tree using locations from the json files.
 */
public class EventGenerator {

    /**
     * Generate an event for the family tree when given a few parameters.
     *
     * @param eventType String event type
     * @param username  String username
     * @param personID  String person id
     * @param year      int year
     * @return Returns the event
     * @throws IOException Error reading the json file.
     */
    public static Event generateEvent(String eventType, String username, String personID, int year) throws IOException {
        try {
            String locations = FileReader.readFile("json/locations.json");
            LocationData locationData = new Gson().fromJson(locations, LocationData.class);
            int randomNumber = new Random().nextInt(locationData.getData().length);

            LocationObject eventLocationObject = locationData.getData()[randomNumber];

            Event event = new Event(UUID.randomUUID().toString(), username, personID, eventLocationObject.getLatitude(), eventLocationObject.getLongitude(), eventLocationObject.getCountry(), eventLocationObject.getCity(), eventType, year);
            return event;
        } catch (IOException e) {
            throw new IOException("Error encountered while parsing json locations");
        }
    }
}
