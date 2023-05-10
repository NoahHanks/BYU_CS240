package DataAccess;

import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDAOTest {

    private Database db;
    private AuthToken bestAuthToken;
    private AuthTokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestAuthToken = new AuthToken("nlasjdhflas8979sdfg", "nhanks10");
        Connection conn = db.getConnection();
        aDao = new AuthTokenDAO(conn);
        aDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Insert AuthToken Test Positive")
    public void insertPass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        AuthToken compareTest = aDao.find(bestAuthToken.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    @DisplayName("Insert AuthToken Test Negative")
    public void insertFail() throws DataAccessException {
        aDao.insert(bestAuthToken);
        assertThrows(DataAccessException.class, () -> aDao.insert(bestAuthToken));
    }

    @Test
    @DisplayName("Find AuthToken Test Positive")
    public void findPass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        AuthToken test = aDao.find(bestAuthToken.getAuthToken());
        assertEquals(bestAuthToken, test);
    }

    @Test
    @DisplayName("Find AuthToken Test Negative")
    public void findFail() throws DataAccessException {
        assertNull(aDao.find(bestAuthToken.getAuthToken()));
    }

    @Test
    @DisplayName("Clear AuthToken Test")
    public void clear() throws DataAccessException {
        aDao.insert(bestAuthToken);
        aDao.clear();
        AuthToken found = aDao.find(bestAuthToken.getAuthToken());
        assertNull(found);
    }

}
