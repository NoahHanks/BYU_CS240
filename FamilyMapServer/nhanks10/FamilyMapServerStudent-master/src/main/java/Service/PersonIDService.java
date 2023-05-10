package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Result.PersonIDResult;

import java.sql.Connection;
import java.util.Objects;

/**
 * Responds to the client by giving them the single person.
 */
public class PersonIDService {

    /**
     * Responds to the client by giving them the single person.
     */
    PersonIDService() {
    }

    /**
     * Returns a single person when given the ID and authToken.
     *
     * @param personID  String of the person's ID.
     * @param authToken The user's authToken.
     * @return The result with the result and message.
     */
    public static PersonIDResult findPerson(String personID, String authToken) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            PersonDAO pDao = new PersonDAO(conn);
            AuthTokenDAO aDao = new AuthTokenDAO(conn);

            Person person = pDao.find(personID);
            if (person == null) {
                db.closeConnection(false);
                return new PersonIDResult("Error: Person not found.");
            }

            AuthToken token = aDao.find(authToken);
            if (token == null) {
                db.closeConnection(false);
                return new PersonIDResult("Error: Invalid AuthToken.");
            }

            if (!Objects.equals(person.getAssociatedUsername(), token.getUsername())) {
                db.closeConnection(false);
                return new PersonIDResult("Error: Person not associated with given user.");
            }

            db.closeConnection(true);
            return new PersonIDResult("Success! Person found.", true, person.getAssociatedUsername(),
                    person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(),
                    person.getSpouseID());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new PersonIDResult("Error: " + e.getMessage());
        }
    }

}
