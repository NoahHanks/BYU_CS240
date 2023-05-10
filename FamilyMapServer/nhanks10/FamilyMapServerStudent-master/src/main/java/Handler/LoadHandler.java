package Handler;

import Request.LoadRequest;
import Result.GenericResult;
import Service.LoadService;
import com.google.gson.Gson;

/**
 * Handles the http exchange by changing the request from the PostHandler
 * to match the result needed for the LoadHandler.
 */
public class LoadHandler extends PostHandler {

    /**
     * Overrides the specific request from the http post request.
     *
     * @param requestData The data that contains info specific to the
     *                    LoadHandler.
     * @return the GenericResult result converted to json.
     */
    @Override
    protected String processRequest(String requestData) {
        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(requestData, LoadRequest.class);
        GenericResult result = LoadService.load(request);
        return gson.toJson(result);
    }
}
