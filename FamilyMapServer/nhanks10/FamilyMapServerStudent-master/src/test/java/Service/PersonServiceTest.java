package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.PersonResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private Database db;
    private User bestUser;
    private AuthToken bestAuthToken;
    private Person bestPerson1;
    private Person bestPerson2;
    private Person bestPerson3;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();

        PersonDAO pDao = new PersonDAO(conn);
        UserDAO uDao = new UserDAO(conn);
        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        db.clearTables();


        bestUser = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        bestAuthToken = new AuthToken("nlasjdhflas8979sdfg", "nhanks10");
        bestPerson1 = new Person("123456789", "nhanks10", "Noah1", "Hanks",
                "m", "11111", "22222", "33333");
        bestPerson2 = new Person("223456789", "nhanks10", "Noah2", "Hanks",
                "m", "11111", "22222", "33333");
        bestPerson3 = new Person("323456789", "nhanks10", "Noah3", "Hanks",
                "m", "11111", "22222", "33333");

        aDao.clear();
        uDao.clear();
        pDao.clear();
        uDao.insert(bestUser);
        aDao.insert(bestAuthToken);
        pDao.insert(bestPerson1);
        pDao.insert(bestPerson2);
        pDao.insert(bestPerson3);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Find Persons Service Test Positive")
    public void getAllPersonsPass() throws DataAccessException {
        PersonResult result = PersonService.getPersonsList(bestAuthToken.getAuthToken());
        assertTrue(result.isSuccess());

        List<Person> expectedPersons = new ArrayList<>();
        expectedPersons.add(bestPerson1);
        expectedPersons.add(bestPerson2);
        expectedPersons.add(bestPerson3);

        assertEquals(expectedPersons, result.getPersons());
    }

    @Test
    @DisplayName("Find Persons Service Test Negative")
    public void getAllPersonsFail() throws DataAccessException {
        PersonResult result = PersonService.getPersonsList("InvalidAuthtoken");
        assertNull(result.getPersons());
        assertFalse(result.isSuccess());
    }
}