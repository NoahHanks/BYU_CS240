package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private Database db;
    private User bestUser;
    private AuthToken bestAuthToken;

    @BeforeEach
    private void setUp() throws DataAccessException {
        bestUser = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        bestAuthToken = new AuthToken("nlasjdhflas8979sdfg", "nhanks10");

        db = new Database();
        Connection conn = db.getConnection();
        UserDAO uDao = new UserDAO(conn);
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        db.clearTables();

        uDao.insert(bestUser);
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
    @DisplayName("Login Service Positive")
    public void loginPass() throws DataAccessException {
        LoginRequest request = new LoginRequest(bestUser.getUsername(), bestUser.getPassword());
        LoginResult result = LoginService.login(request);
        assertTrue(result.isSuccess());
        assertNotEquals(result.getAuthtoken(), bestAuthToken.getAuthToken());
    }

    @Test
    @DisplayName("Login Service Negative")
    public void loginFail() throws DataAccessException {
        LoginRequest request = new LoginRequest("InvalidUsername", "InvalidPassword");
        LoginResult result = LoginService.login(request);
        assertFalse(result.isSuccess());
    }
}
