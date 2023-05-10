package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.EventResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private Database db;
    private Event bestEvent;
    private Event bestEvent2;
    private AuthToken testAuthToken;

    @BeforeEach
    private void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();

        EventDAO eDao = new EventDAO(conn);
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        bestEvent2 = new Event("Wedding", "Gale", "Gale123A",
                100.1f, 200.2f, "USA", "Provo",
                "Marriage", 2022);
        eDao.insert(bestEvent);
        eDao.insert(bestEvent2);

        AuthTokenDAO aDao = new AuthTokenDAO(conn);
        testAuthToken = new AuthToken("nlasjdhflas8979sdfg", "Gale");
        aDao.insert(testAuthToken);

        db.closeConnection(true);
    }

    @AfterEach
    private void tearDown() throws DataAccessException {
        db.getConnection();
        db.clearTables();
        db.closeConnection(false);
    }

    @Test
    @DisplayName("Get Events Service Test Positive")
    public void getEventsPass() {
        EventResult result = EventService.getEventsList(testAuthToken.getAuthToken());
        assertTrue(result.isSuccess());

        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(bestEvent);
        expectedEvents.add(bestEvent2);
        assertEquals(expectedEvents, result.getEvents());
    }

    @Test
    @DisplayName("Get Events Service Test Negative")
    public void getEventsFail() {
        EventResult result = EventService.getEventsList("InvalidAuthtoken");
        assertFalse(result.isSuccess());
    }
}
