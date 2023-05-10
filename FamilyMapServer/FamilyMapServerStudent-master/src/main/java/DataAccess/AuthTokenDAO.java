package DataAccess;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connects to the database and manipulates the tables using the AuthToken and SQL.
 */
public class AuthTokenDAO {

    /**
     * Connection to the database.
     */
    public final Connection conn;

    /**
     * AuthTokenDAO Constructor
     *
     * @param conn Connection for the database.
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an authtoken into the database.
     *
     * @param authtoken AuthToken object
     * @throws DataAccessException Error inserting the object into the database.
     */
    public void insert(AuthToken authtoken) throws DataAccessException {
        String sql = "INSERT INTO Tokens (authtoken, username) VALUES(?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthToken());
            stmt.setString(2, authtoken.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * Finds an authToken in the database.
     *
     * @param authtoken String
     * @return AuthToken object
     * @throws DataAccessException Error finding the object in the database.
     */
    public AuthToken find(String authtoken) throws DataAccessException {
        AuthToken token;
        ResultSet rs;
        String sql = "SELECT * FROM Tokens WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return token;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }

    /**
     * Clears the AuthToken table from the database.
     *
     * @throws DataAccessException Error if unable to clear the Authtoken table.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Tokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtokens table");
        }
    }
}
