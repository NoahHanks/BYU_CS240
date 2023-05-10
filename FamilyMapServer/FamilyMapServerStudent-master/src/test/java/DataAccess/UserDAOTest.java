package DataAccess;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDAOTest {
    private Database db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        Connection conn = db.getConnection();
        uDao = new UserDAO(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Insert User Test Positive")
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    @DisplayName("Insert User Test Negative")
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    @DisplayName("Find User Test Positive")
    public void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        User found = uDao.find(bestUser.getUsername());
        assertNotNull(found);
    }

    @Test
    @DisplayName("Find User Test Negative")
    public void findFail() throws DataAccessException {
        assertNull(uDao.find(bestUser.getUsername()));
    }

    @Test
    @DisplayName("Clear User Test")
    public void clearUsers() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.clear();
        User found = uDao.find(bestUser.getUsername());
        assertNull(found);
    }

}
