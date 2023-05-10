package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accesses the data objects of the person table.
 */
public class PersonDAO {

    /**
     * Connection to the database.
     */
    public final Connection conn;

    /**
     * PersonDAO Constructor
     *
     * @param conn Connection for the database.
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Sets the connection to the database.
     *
     * @param conn Connection for the database.
     */
    public void setConnection(Connection conn) {

    }

    /**
     * Inserts a person into the database.
     *
     * @param person Person object
     * @throws DataAccessException Error inserting the object into the database.
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, " +
                "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting person into the database");
        }
    }

    /**
     * Finds an event in the database.
     *
     * @param personID String
     * @return Event object
     * @throws DataAccessException Error finding the object in the database.
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Returns all persons associated with the provided username
     *
     * @param username user's username
     * @return array of all persons associated with the provided username
     * @throws DataAccessException if error occurs while accessing persons within the database
     */
    public List<Person> getPersonList(String username) throws DataAccessException {
        List<Person> persons = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Person person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                        rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
                persons.add(person);
            }
            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all persons associated with " + username);
        }
    }

    /**
     * Clears database of all persons associated with a specific user
     *
     * @param username user's username
     * @throws DataAccessException if error encountered while clearing db
     */
    public void clear(String username) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE AssociatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing persons from a specific user");
        }
    }

    /**
     * Clear all people from the database
     *
     * @throws DataAccessException if error encountered while clearing the people table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    /**
     * Updates the parents of a giver person using their personID.
     *
     * @param personID String unique identifier
     * @param fatherID String father id
     * @param motherID String mother id
     * @throws DataAccessException Error while accessing the database.
     */
    public void updateParents(String personID, String fatherID, String motherID) throws DataAccessException {
        String sql = "UPDATE Persons SET FatherID = ?, MotherID = ? WHERE PersonID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fatherID);
            stmt.setString(2, motherID);
            stmt.setString(3, personID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error: Could not update parents of " + personID);
        }
    }
}
