package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.EventIDResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventIDServiceTest {
    private Database db;
    private Event bestEvent;
    private Event bestEvent2;
    private AuthToken testAuthToken;

    @BeforeEach
    private void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();

        EventDAO eDao = new EventDAO(conn);
        db.clearTables();

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
        db.closeConnection(true);
    }

    @Test
    @DisplayName("Find Event Service Test Positive")
    public void findPass() throws DataAccessException {
        EventIDResult result = EventIDService.getEvent(bestEvent.getEventID(), testAuthToken.getAuthToken());
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("Find Event Service Test Negative")
    public void findFail() throws DataAccessException {
        EventIDResult result = EventIDService.getEvent(bestEvent.getEventID(), "InvalidAuthToken");
        assertFalse(result.isSuccess());
        EventIDResult result2 = EventIDService.getEvent("InvalidID", "InvalidToken");
        assertFalse(result2.isSuccess());
    }


}
