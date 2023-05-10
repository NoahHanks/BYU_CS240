package DataAccess;

import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the data objects of the events table.
 */
public class EventDAO {

    /**
     * Connection to the database.
     */
    public final Connection conn;

    /**
     * EventDAO Constructor
     *
     * @param conn Connection for the database.
     */
    public EventDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an event into the database.
     *
     * @param event Event object
     * @throws DataAccessException Error inserting the object into the database.
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds an event in the database.
     *
     * @param eventID String
     * @return Event object
     * @throws DataAccessException Error finding the object in the database.
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Returns all events associated with the provided username.
     *
     * @param username String username
     * @return A list of all the events connected to a person's username.
     * @throws DataAccessException Error creating the list in the database.
     */
    public List<Event> getEventList(String username) throws DataAccessException {
        List<Event> events = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                        rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                        rs.getInt("Year"));
                events.add(event);
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all events associated with " + username);
        }
    }

    /**
     * Clears the Event table from the database.
     *
     * @throws DataAccessException Error clearing the event table from the database.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Events";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    /**
     * Clears all the events for a given username.
     *
     * @param username String username.
     * @throws DataAccessException Error accessing the database.
     */
    public void clear(String username) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE AssociatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error. Could not clear events for given username.");
        }
    }

}
