package Result;

import Model.Event;

import java.util.List;

/**
 * Makes a result for when the client requests all the events.
 */
public class EventResult extends GenericResult {

    /**
     * List of events.
     */
    private List<Event> data;

    /**
     * Sends the result with all the events as well as a message and success flag.
     *
     * @param message String message
     * @param success Boolean success flag
     * @param events  List of events
     */
    public EventResult(String message, boolean success, List<Event> events) {
        super(message, success);
        this.data = events;
    }

    public EventResult(String message) {
        super(message, false);
    }

    public List<Event> getEvents() {
        return data;
    }

    public void setEvents(List<Event> events) {
        this.data = events;
    }
}
