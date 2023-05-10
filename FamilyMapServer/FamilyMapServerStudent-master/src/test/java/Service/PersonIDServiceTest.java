package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Result.PersonIDResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonIDServiceTest {

    private PersonIDService service;
    private Database db;
    private PersonDAO pDao;
    private AuthTokenDAO aDao;
    private Person bestPerson;
    private AuthToken bestAuthToken;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        PersonDAO pDao = new PersonDAO(conn);
        AuthTokenDAO aDao = new AuthTokenDAO(conn);

        service = new PersonIDService();

        bestPerson = new Person("123456789", "nhanks10", "Noah", "Hanks",
                "m", "11111", "22222", "33333");
        bestAuthToken = new AuthToken("nlasjdhflas8979sdfg", "nhanks10");

        pDao.clear();
        aDao.clear();
        aDao.insert(bestAuthToken);
        pDao.insert(bestPerson);

        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Find Person Service Test Positive")
    public void findPersonPass() throws DataAccessException {
        PersonIDResult result = service.findPerson(bestPerson.getPersonID(), bestAuthToken.getAuthToken());
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("Find Person Service Test Positive")
    public void findPersonFail() throws DataAccessException {
        PersonIDResult result = service.findPerson(bestPerson.getPersonID(), "invalidAuthtoken");
        assertFalse(result.isSuccess());
    }

}