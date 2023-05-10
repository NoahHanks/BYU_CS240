package Handler;

import Request.RegisterRequest;
import Result.LoginResult;
import Service.RegisterService;
import com.google.gson.Gson;

/**
 * Handles the http exchange by changing the request from the PostHandler
 * to match the result needed for the RegisterHandler.
 */
public class RegisterHandler extends PostHandler {

    /**
     * Overrides the specific request from the http post request.
     *
     * @param requestData The data that contains info specific to the
     *                    RegisterHandler.
     * @return the RegisterHandler result converted to json.
     */
    @Override
    protected String processRequest(String requestData) {
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(requestData, RegisterRequest.class);
        LoginResult result = RegisterService.register(request);
        return gson.toJson(result);
    }
}
