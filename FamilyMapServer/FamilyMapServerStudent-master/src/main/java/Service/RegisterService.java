package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Generator.NewUserGenerator;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.LoginResult;

import java.io.IOException;
import java.sql.Connection;
import java.util.UUID;

/**
 * Performs the services for when the user registers.
 */
public class RegisterService {

    /**
     * Logs in the user when registering.
     *
     * @param request Takes a register request with all the user info.
     * @return Gives an authToken for a successful register.
     */
    public static LoginResult register(RegisterRequest request) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();

            UserDAO uDao = new UserDAO(conn);
            if (uDao.find(request.getUsername()) != null) {
                db.closeConnection(false);
                return new LoginResult("Error: Username already taken.");
            }
            User user = new User(request.getUsername(), request.getPassword(), request.getEmail(), request.getFirstName(),
                    request.getLastName(), request.getGender(), UUID.randomUUID().toString());
            uDao.insert(user);

            AuthTokenDAO aDao = new AuthTokenDAO(conn);
            AuthToken token = new AuthToken(UUID.randomUUID().toString(), user.getUsername());
            aDao.insert(token);

            NewUserGenerator.newUserGenerateTree(conn, user, 4);

            db.closeConnection(true);
            return new LoginResult("Successfully registered.", true, token.getAuthToken(), user.getUsername(), user.getPersonID());
        } catch (DataAccessException | IOException e) {
            e.printStackTrace();
            db.closeConnection(false);
            return new LoginResult("Error: " + e.getMessage());
        }
    }
}
