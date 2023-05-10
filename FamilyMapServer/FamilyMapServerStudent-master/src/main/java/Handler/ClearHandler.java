package Handler;

import Result.GenericResult;
import Service.ClearService;
import com.google.gson.Gson;

/**
 * Handles the http exchange by changing the request from the PostHandler
 * to match the result needed for the ClearHandler.
 */
public class ClearHandler extends PostHandler {

    /**
     * Overrides the specific request from the http post request.
     *
     * @param requestData The data that contains info specific to the
     *                    ClearHandler.
     * @return the GenericResult result converted to json.
     */
    @Override
    protected String processRequest(String requestData) {
        GenericResult result = ClearService.clear();
        Gson gson = new Gson();
        return gson.toJson(result);
    }
}
