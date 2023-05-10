package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.EventIDResult;

import java.sql.Connection;

/**
 * Responds to the client by giving them the single event.
 */
public class EventIDService {

    /**
     * Returns a single event when given the ID and authToken.
     *
     * @param eventID   String of the event's ID.
     * @param authToken The user's authToken.
     * @return The result with the event and message.
     */
    public static EventIDResult getEvent(String eventID, String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            EventDAO eDao = new EventDAO(conn);
            AuthTokenDAO aDao = new AuthTokenDAO(conn);

            Event event = eDao.find(eventID);
            if (event == null) {
                db.closeConnection(false);
                return new EventIDResult("Error: Event not found or invalid eventID.");
            }

            AuthToken token = aDao.find(authToken);
            if (token == null || token.getUsername().equals(event.getAssociatedUsername()) == false) {
                db.closeConnection(false);
                return new EventIDResult("Error: Invalid AuthToken.");
            }

            db.closeConnection(true);

            return new EventIDResult("Success! Event found.", true,
                    event.getAssociatedUsername(),
                    event.getEventID(),
                    event.getPersonID(),
                    event.getLatitude(),
                    event.getLongitude(),
                    event.getCountry(),
                    event.getCity(),
                    event.getEventType(),
                    event.getYear());

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new EventIDResult("Error: Event not found.");

        }
    }
}
