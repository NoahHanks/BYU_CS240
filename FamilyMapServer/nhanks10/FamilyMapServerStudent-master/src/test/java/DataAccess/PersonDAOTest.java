package DataAccess;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("123456789", "nhanks10", "Noah", "Hanks",
                "m", "11111", "22222", "33333");
        Connection conn = db.getConnection();
        pDao = new PersonDAO(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Insert Person Test Positive")
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    @DisplayName("Insert Person Test Negative")
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    @DisplayName("Find Person Test Positive")
    public void findPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person found = pDao.find(bestPerson.getPersonID());
        assertNotNull(found);
    }

    @Test
    @DisplayName("Find Person Test Negative")
    public void findFail() throws DataAccessException {
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    @DisplayName("Clear Person Test")
    public void clearPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.clear();
        Person found = pDao.find(bestPerson.getPersonID());
        assertNull(found);
    }

}
