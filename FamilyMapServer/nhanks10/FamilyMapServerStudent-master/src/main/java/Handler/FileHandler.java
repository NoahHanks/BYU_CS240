package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * Handles the http exchange for the EventID api call from the html site.
 */
public class FileHandler extends Handler implements HttpHandler {
    /**
     * Handles the exchange through http.
     *
     * @param exchange Contains the http request.
     * @throws IOException Error with input from the user.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String urlPath = exchange.getRequestURI().toString().toLowerCase();

                if (urlPath.equals("/")) urlPath = "/index.html";

                File file = new File("web" + urlPath);

                if (file.exists()) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    file = new File("web/HTML/404.html");
                }

                OutputStream responseBody = exchange.getResponseBody();
                Files.copy(file.toPath(), responseBody);
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
