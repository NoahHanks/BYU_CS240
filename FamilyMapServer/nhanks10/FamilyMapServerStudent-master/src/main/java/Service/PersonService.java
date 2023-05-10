package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Result.PersonResult;

import java.sql.Connection;
import java.util.List;

/**
 * Responds to the client by giving them all the persons.
 */
public class PersonService {

    /**
     * Constructor for the PersonService.
     */
    PersonService() {
    }

    /**
     * Returns all the persons when given the authToken.
     *
     * @param authToken current user's auth token
     * @return The result with all the persons and message.
     */
    public static PersonResult getPersonsList(String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            AuthToken token = aDao.find(authToken);

            if (token == null) {
                db.closeConnection(false);
                return new PersonResult("Error: Invalid AuthToken.");
            }

            String currentUser = token.getUsername();
            PersonDAO pDao = new PersonDAO(conn);
            List<Person> persons = pDao.getPersonList(currentUser);

            db.closeConnection(true);
            return new PersonResult("Success! All persons found.", true, persons);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new PersonResult("Error: " + e.getMessage());
        }
    }
}
