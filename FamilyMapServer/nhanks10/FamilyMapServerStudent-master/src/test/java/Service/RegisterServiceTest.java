package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.LoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private Database db;
    private Connection conn;
    private User bestUser;
    private AuthToken bestAuthToken;

    @BeforeEach
    private void setUp() throws DataAccessException {
        bestUser = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        bestAuthToken = new AuthToken("nlasjdhflas8979sdfg", "nhanks10");

        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        aDao.insert(bestAuthToken);

        db.closeConnection(true);
    }

    @AfterEach
    private void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    @DisplayName("Register Service Test Positive")
    public void registerPass() {
        RegisterRequest request = new RegisterRequest(bestUser.getUsername(), bestUser.getPassword(), bestUser.getEmail(),
                bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());

        LoginResult result = RegisterService.register(request);
        assertTrue(result.isSuccess());

        assertNotEquals(result.getAuthtoken(), bestAuthToken.getAuthToken());
    }

    @Test
    @DisplayName("Register Service Test Negative")
    public void registerFail() throws DataAccessException {
        Connection conn = db.getConnection();
        UserDAO uDao = new UserDAO(conn);
        uDao.insert(bestUser);

        RegisterRequest request = new RegisterRequest(bestUser.getUsername(), bestUser.getPassword(), bestUser.getEmail(),
                bestUser.getFirstName(), bestUser.getLastName(), bestUser.getGender());
        LoginResult result = RegisterService.register(request);

        assertFalse(result.isSuccess());
    }
}
