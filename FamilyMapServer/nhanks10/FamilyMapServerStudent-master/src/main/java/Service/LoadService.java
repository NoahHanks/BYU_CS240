package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.GenericResult;

import java.util.ArrayList;

/**
 * Load the database with information provided by the client.
 */
public class LoadService {

    /**
     * Loads info into the database from the LoadRequest.
     *
     * @param request Takes in a request with arrays for users, events, and persons.
     * @return Sends the result with a message and success flag.
     */
    public static GenericResult load(LoadRequest request) {
        Database db = new Database();

        try {
            db.openConnection();
            UserDAO uDao = new UserDAO(db.getConnection());
            EventDAO eDao = new EventDAO(db.getConnection());
            PersonDAO pDao = new PersonDAO(db.getConnection());
            AuthTokenDAO aDao = new AuthTokenDAO(db.getConnection());
            uDao.clear();
            eDao.clear();
            pDao.clear();
            aDao.clear();

            ArrayList<User> users = request.getUsers();
            ArrayList<Person> persons = request.getPersons();
            ArrayList<Event> events = request.getEvents();

            int insertedUsers = 0;
            for (User user : users) {
                uDao.insert(user);
                insertedUsers++;
            }

            int insertedPersons = 0;
            for (Person person : persons) {
                pDao.insert(person);
                insertedPersons++;
            }

            int insertedEvents = 0;
            for (Event event : events) {
                eDao.insert(event);
                insertedEvents++;
            }

            db.closeConnection(true);
            return new GenericResult("Successfully added " +
                    insertedUsers + " users, " +
                    insertedPersons + " persons, and " +
                    insertedEvents + " events to the database.", true);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new GenericResult("Exception thrown while loading the database.", false);
        }
    }
}
