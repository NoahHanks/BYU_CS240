package Generator;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Model.Event;
import Model.Person;
import Model.User;

import java.io.IOException;
import java.sql.Connection;

import static Generator.EventGenerator.generateEvent;
import static Generator.TreeGenerator.generateTree;

/**
 * Generates the family tree for a new user.
 */
public class NewUserGenerator {
    /**
     * Generates a family tree for the user when they use the fill service or register.
     *
     * @param conn           Connection to the database.
     * @param user       String of the user of the main person in the tree.
     * @param generations How many generations to add with zero being just the user.
     * @throws IOException         Error accessing any files to generate data.
     * @throws DataAccessException Error accessing the database.
     */
    public static void newUserGenerateTree(Connection conn, User user, int generations) throws DataAccessException, IOException {
        PersonDAO pDao = new PersonDAO(conn);
        EventDAO eDao = new EventDAO(conn);
        Person person = pDao.find(user.getPersonID());
        if (person != null) {
            pDao.clear(user.getUsername());
            eDao.clear(user.getUsername());
        }

        Person userPerson = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getGender());
        pDao.insert(userPerson);

        Event userBirth = generateEvent("Birth", user.getUsername(), user.getPersonID(), 2022);
        eDao.insert(userBirth);

        generateTree(conn, user.getPersonID(), generations, userBirth.getYear() - 30);
    }
}
