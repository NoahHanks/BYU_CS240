package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;

import java.sql.Connection;
import java.util.List;

/**
 * Responds to the client by giving them all the events.
 */
public class EventService {

    /**
     * Returns all the events when given the authToken.
     *
     * @param authToken current user's auth token
     * @return The result with all the events and message.
     */
    public static EventResult getEventsList(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            EventDAO eDao = new EventDAO(conn);

            AuthToken token = aDao.find(authToken);
            if (token == null) {
                db.closeConnection(false);
                return new EventResult("Error: Invalid AuthToken.");
            }

            List<Event> events = eDao.getEventList(token.getUsername());

            db.closeConnection(true);
            return new EventResult("Success! All events found.", true, events);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new EventResult("Error: " + e.getMessage());
        }
    }
}
