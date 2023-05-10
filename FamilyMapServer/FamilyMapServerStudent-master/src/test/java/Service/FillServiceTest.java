package Service;

import DataAccess.*;
import Model.User;
import Request.FillRequest;
import Result.GenericResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FillServiceTest {

    private FillService service;
    private Database db;
    private EventDAO eDao;
    private AuthTokenDAO aDao;
    private UserDAO uDao;
    private User bestUser;

    @BeforeEach
    public void setUp() throws DataAccessException {
        service = new FillService();

        bestUser = new User("nhanks10", "Incorrect1", "noahhanks10@gmail.com",
                "Noah", "Hanks", "m", "123456789");
        db = new Database();
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthTokenDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        eDao.clear();
        aDao.clear();
        uDao.clear();
        uDao.insert(bestUser);
        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db = new Database();
        db.openConnection();
        eDao = new EventDAO(db.getConnection());
        aDao = new AuthTokenDAO(db.getConnection());
        uDao = new UserDAO(db.getConnection());
        eDao.clear();
        aDao.clear();
        uDao.clear();
        db.closeConnection(true);
    }

    @Test
    @DisplayName("Fill Service Test Positive")
    public void fillPass() {

        FillRequest request = new FillRequest("nhanks10", 3);
        GenericResult result = service.fill(request);
        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("Fill Service Test Negative")
    public void fillFail() throws DataAccessException {
        FillRequest request = new FillRequest("FailTest", 3);
        GenericResult result = service.fill(request);
        assertFalse(result.isSuccess());
    }
}