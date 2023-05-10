package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Generator.NewUserGenerator;
import Model.User;
import Request.FillRequest;
import Result.GenericResult;

import java.io.IOException;
import java.sql.Connection;

/**
 * The service to fill a user's family tree with random data taken from json files.
 */
public class FillService {

    /**
     * Constructor for the fill service.
     */
    FillService() {
    }

    /**
     * Fills the database with family generation data given a fill request with user info.
     *
     * @param req A fill request object with the user's username as well as a generation count
     *            either the default of 4 or a custom amount.
     * @return A basic result object message with a success flag.
     */
    public static GenericResult fill(FillRequest req) {
        String username = req.getUsername();
        int generations = req.getGenCount();
        Database db = new Database();

        if (generations < 0) {
            return new GenericResult("Error: Generation count must be zero or greater.", false);
        }

        try {
            Connection conn = db.getConnection();
            UserDAO uDao = new UserDAO(conn);
            User user = uDao.find(username);
            if (user == null) {
                db.closeConnection(false);
                return new GenericResult("Error: User not found.", false);
            }

            NewUserGenerator.newUserGenerateTree(conn, user, generations);
            db.closeConnection(true);

            int personsAdded = (int) Math.round(Math.pow(2, generations + 1f) - 1f);
            int eventsAdded = (int) Math.round((Math.pow(2, generations + 1f) - 1f) * 3f - 2f);

            String resultMessage = ("Successfully added " +
                    personsAdded + " persons and " +
                    eventsAdded + " events to the database.");
            return new GenericResult(resultMessage, true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new GenericResult("Error accessing database.", false);
        } catch (IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new GenericResult("Error filling family tree.", false);
        }
    }
}
