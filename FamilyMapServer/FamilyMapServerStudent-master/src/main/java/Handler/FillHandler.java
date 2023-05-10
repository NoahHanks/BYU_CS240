package Handler;

import Request.FillRequest;
import Result.GenericResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Handles the http exchange for the EventID api call from the html site.
 */
public class FillHandler extends Handler implements HttpHandler {
    /**
     * Handles the exchange through http.
     *
     * @param exchange Contains the http request which is sent to the service.
     * @throws IOException Error with input from the user.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                String urlPath = exchange.getRequestURI().toString().substring(6);

                FillRequest request;
                if (urlPath.contains("/")) {
                    String[] urlPathSplit = urlPath.split("/");
                    String username = urlPathSplit[0];
                    int numGenerations = Integer.parseInt(urlPathSplit[1]);
                    request = new FillRequest(username, numGenerations);
                } else {
                    String username = urlPath;
                    request = new FillRequest(username);
                }

                GenericResult result = FillService.fill(request);
                Gson gson = new Gson();
                String responseData = gson.toJson(result);

                if (responseData.contains("true")) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream responseBody = exchange.getResponseBody();
                writeString(responseData, responseBody);
                responseBody.close();
                success = true;
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
