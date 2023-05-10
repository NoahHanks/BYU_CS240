package DataAccess;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Accesses the data objects of the user table.
 */
public class UserDAO {

    /**
     * Connection to the database.
     */
    public Connection conn;

    /**
     * UserDAO Constructor
     *
     * @param conn Connection for the database.
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Sets the connection to the database.
     *
     * @param conn Connection for the database.
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a user into the database.
     *
     * @param user User object
     * @throws DataAccessException Error inserting the object into the database.
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (Username, Password, Email, FirstName, " +
                "LastName, Gender, PersonID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting user into the database");
        }
    }

    /**
     * Finds an authToken in the database.
     *
     * @param username String
     * @return User object
     * @throws DataAccessException Error finding the object in the database.
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Email"), rs.getString("FirstName"), rs.getString("LastName"),
                        rs.getString("Gender"), rs.getString("PersonID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Clears the User table from the database.
     *
     * @throws DataAccessException Error clearing the user table from the database.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the users table in the database");
        }
    }
}
