package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Result.GenericResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClearServiceTest {

    private ClearService service;
    private Database db;
    private User bestUser;
    private AuthToken bestAuthToken;
    private Person bestPerson;
    private Event bestEvent;
    private UserDAO uDao;
    private AuthTokenDAO aDao;
    private EventDAO eDao;
    private PersonDAO pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new ClearService();

        bestUser = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");

        bestAuthToken = new AuthToken("nlasjdhflas8979sdfg", "nhanks10");

        bestEvent = new Event("Biking_123A", "nhanks10", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        bestPerson = new Person("123456789", "nhanks10", "Noah", "Hanks",
                "m", "11111", "22222", "33333");

        db = new Database();
        db.openConnection();
        uDao = new UserDAO(db.getConnection());
        aDao = new AuthTokenDAO(db.getConnection());
        eDao = new EventDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        db.clearTables();

        uDao.insert(bestUser);
        aDao.insert(bestAuthToken);
        eDao.insert(bestEvent);
        pDao.insert(bestPerson);
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Clear Service Test Positive")
    public void clearPass() throws DataAccessException {
        GenericResult result = service.clear();
        assertEquals(true, result.isSuccess());

        db.openConnection();
        uDao = new UserDAO(db.getConnection());
        aDao = new AuthTokenDAO(db.getConnection());
        eDao = new EventDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        assertNull(uDao.find(bestUser.getUsername()));
        assertNull(aDao.find(bestAuthToken.getAuthToken()));
        assertNull(eDao.find(bestEvent.getEventID()));
        assertNull(pDao.find(bestPerson.getPersonID()));
        db.closeConnection(false);
    }
}