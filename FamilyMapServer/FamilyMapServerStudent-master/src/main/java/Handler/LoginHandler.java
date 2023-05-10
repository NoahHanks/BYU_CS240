package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;

/**
 * Handles the http exchange by changing the request from the PostHandler
 * to match the result needed for the LoginHandler.
 */
public class LoginHandler extends PostHandler {

    /**
     * Overrides the specific request from the http post request.
     *
     * @param requestData The data that contains info specific to the
     *                    LoginHandler.
     * @return the LoginHandler result converted to json.
     */
    @Override
    protected String processRequest(String requestData) {
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(requestData, LoginRequest.class);
        LoginResult result = LoginService.login(request);
        return gson.toJson(result);
    }
}
