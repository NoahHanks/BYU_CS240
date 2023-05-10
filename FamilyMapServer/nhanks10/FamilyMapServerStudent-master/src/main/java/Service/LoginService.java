package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * Service to let the user login.
 */
public class LoginService {

    /**
     * Creates the result when the user logs in.
     *
     * @param request Takes a request to login from the user.
     * @return Returns a LoginResult when successfully logging in.
     */
    public static LoginResult login(LoginRequest request) {
        Database db = new Database();
        AuthToken authtoken;

        try {
            Connection conn = db.getConnection();
            UserDAO uDao = new UserDAO(conn);

            User user = uDao.find(request.getUsername());
            if (user != null) {
                if (user.getPassword().equals(request.getPassword())) {
                    authtoken = new AuthToken(UUID.randomUUID().toString(), user.getUsername());
                    AuthTokenDAO aDao = new AuthTokenDAO(conn);
                    aDao.insert(authtoken);
                } else {
                    db.closeConnection(false);
                    return new LoginResult("Error: Invalid password.", false);
                }
            } else {
                db.closeConnection(false);
                return new LoginResult("Error: Invalid username.", false);
            }

            db.closeConnection(true);
            return new LoginResult("Successfully logged in.", true, authtoken.getAuthToken(), user.getUsername(), user.getPersonID());
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoginResult("Login exception thrown.", false);
        }

    }
}
