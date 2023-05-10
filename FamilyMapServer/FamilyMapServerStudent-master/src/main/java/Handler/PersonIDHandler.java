package Handler;

import Result.PersonIDResult;
import Service.PersonIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Handles the http exchange for the PersonID api call from the html site.
 */
public class PersonIDHandler extends Handler implements HttpHandler {
    /**
     * Handles the exchange through http.
     *
     * @param exchange Contains the http request which is sent to the service.
     * @throws IOException Error with input from the user.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String urlPath = exchange.getRequestURI().toString();
                String[] str = urlPath.split("/");
                String personID = str[2];

                if (personID != null) {
                    Headers requestHeaders = exchange.getRequestHeaders();
                    if (requestHeaders.containsKey("Authorization")) {
                        String authToken = requestHeaders.getFirst("Authorization");
                        if (authToken != null) {
                            PersonIDResult result = PersonIDService.findPerson(personID, authToken);
                            Gson gson = new Gson();
                            String responseData = gson.toJson(result);

                            if (result.isSuccess()) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            else exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            OutputStream responseBody = exchange.getResponseBody();
                            writeString(responseData, responseBody);
                            responseBody.close();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
