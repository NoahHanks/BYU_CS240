package Generator;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Model.Event;
import Model.Person;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

import static Generator.EventGenerator.generateEvent;
import static Generator.PersonGenerator.generatePerson;

/**
 * The main generator of the family tree that parses the json files and creates and adds
 * people to the database.
 */
public class TreeGenerator {

    /**
     * Recursively generates the family tree for the current person.
     * Recursion is based on the current generation and
     *
     * @param conn           Connection to the database.
     * @param personID       String of the personID of the main person in the tree.
     * @param currGeneration How many generations to add with zero being just the user.
     * @param currYear       Birth year of the parents.
     * @throws IOException         Error accessing any files to generate data.
     * @throws DataAccessException Error accessing the database.
     */
    public static void generateTree(Connection conn, String personID, int currGeneration, int currYear) throws IOException, DataAccessException {
        PersonDAO pDao = new PersonDAO(conn);
        EventDAO eDao = new EventDAO(conn);
        Person currPerson = pDao.find(personID);

        // Create the mother and father persons.
        Person mother = generatePerson(currPerson.getAssociatedUsername(), "f", null);
        Person father = generatePerson(currPerson.getAssociatedUsername(), "m", currPerson.getLastName());
        mother.setSpouseID(father.getPersonID());
        father.setSpouseID(mother.getPersonID());

        if (currGeneration > 0) {
            // Update parents of current person then add parents to database.
            pDao.updateParents(personID, father.getPersonID(), mother.getPersonID());
            pDao.insert(mother);
            pDao.insert(father);

            String associatedUsername = currPerson.getAssociatedUsername();
            String motherID = mother.getPersonID();
            String fatherID = father.getPersonID();

            // Create and add parent's births.
            Event motherBirth = generateEvent("Birth", associatedUsername, motherID, currYear);
            Event fatherBirth = generateEvent("Birth", associatedUsername, fatherID, currYear);
            eDao.insert(motherBirth);
            eDao.insert(fatherBirth);

            // Create and add parent's marriage.
            Event marriage = generateEvent("Marriage", associatedUsername, motherID, currYear + 25);
            eDao.insert(marriage);
            marriage.setPersonID(fatherID);
            marriage.setEventID(UUID.randomUUID().toString());
            eDao.insert(marriage);

            // Create and add parent's deaths.
            Event motherDeath = generateEvent("Death", associatedUsername, motherID, currYear + 80);
            Event fatherDeath = generateEvent("Death", associatedUsername, fatherID, currYear + 80);
            eDao.insert(motherDeath);
            eDao.insert(fatherDeath);

            generateTree(conn, mother.getPersonID(), currGeneration - 1, currYear - 30);
            generateTree(conn, father.getPersonID(), currGeneration - 1, currYear - 30);
        }
    }
}
