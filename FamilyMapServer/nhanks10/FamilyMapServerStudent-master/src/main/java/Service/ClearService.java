package Service;

import DataAccess.*;
import Result.GenericResult;

import java.sql.Connection;

/**
 * Performs the Clear Service.
 */
public class ClearService {

    /**
     * Clears all the tables in the database.
     *
     * @return a result object.
     */
    public static GenericResult clear() {
        Database db = new Database();

        try {
            Connection conn = db.getConnection();
            UserDAO uDao = new UserDAO(conn);
            EventDAO eDao = new EventDAO(conn);
            PersonDAO pDao = new PersonDAO(conn);
            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            uDao.clear();
            eDao.clear();
            pDao.clear();
            aDao.clear();
            db.closeConnection(true);
            return new GenericResult("Clear succeeded.", true);

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new GenericResult("ClearService exception caught.", false);
        }
    }
}
