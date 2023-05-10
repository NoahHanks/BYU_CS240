package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.GenericResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoadServiceTest {

    private LoadService service;
    private User bestUser1;
    private User bestUser2;
    private User bestUser3;
    private Event bestEvent1;
    private Event bestEvent2;
    private Event bestEvent3;
    private Person bestPerson1;
    private Person bestPerson2;
    private Person bestPerson3;
    private Database db;
    private EventDAO eDao;
    private PersonDAO pDao;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        service = new LoadService();
        eDao = new EventDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        eDao.clear();
        uDao.clear();
        pDao.clear();
        db.closeConnection(true);
    }

    @Test
    @DisplayName("Load Service Test Positive")
    public void loadPass() throws DataAccessException {
        bestUser1 = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        bestUser2 = new User("nhanks11", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        bestUser3 = new User("nhanks12", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        ArrayList<User> users = new ArrayList<User>();
        users.add(bestUser1);
        users.add(bestUser2);
        users.add(bestUser3);

        bestPerson1 = new Person("123456789", "nhanks10", "Noah", "Hanks",
                "m", "11111", "22222", "33333");
        bestPerson2 = new Person("223456789", "nhanks11", "Noah", "Hanks",
                "m", "11111", "22222", "33333");
        bestPerson3 = new Person("323456789", "nhanks12", "Noah", "Hanks",
                "m", "11111", "22222", "33333");
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(bestPerson1);
        persons.add(bestPerson2);
        persons.add(bestPerson3);


        bestEvent1 = new Event("Biking_123A", "nhanks10", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        bestEvent2 = new Event("Wedding", "nhanks11", "Gale123A",
                100.1f, 200.2f, "USA", "Provo",
                "Marriage", 2022);
        bestEvent3 = new Event("Birthday", "nhanks12", "Gale123A",
                200.1f, 300.2f, "USA", "Los Angeles",
                "Birth", 1999);
        ArrayList<Event> events = new ArrayList<Event>();
        events.add(bestEvent1);
        events.add(bestEvent2);
        events.add(bestEvent3);

        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        pDao = new PersonDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());

        LoadRequest request = new LoadRequest(users, persons, events);
        GenericResult result = LoadService.load(request);
        assertTrue(result.isSuccess());
        assertNotNull(uDao.find("nhanks10"));
        assertNotNull(uDao.find("nhanks11"));
        assertNotNull(uDao.find("nhanks12"));

        assertNotNull(pDao.find("123456789"));
        assertNotNull(pDao.find("223456789"));
        assertNotNull(pDao.find("323456789"));

        assertNotNull(eDao.find("Biking_123A"));
        assertNotNull(eDao.find("Wedding"));
        assertNotNull(eDao.find("Birthday"));
        db.closeConnection(false);
    }
}