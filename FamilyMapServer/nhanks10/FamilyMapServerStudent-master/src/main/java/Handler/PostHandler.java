package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PostHandler extends Handler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                InputStream jsonRequest = exchange.getRequestBody();
                String requestData = readString(jsonRequest);
                String jsonResult = processRequest(requestData);

                if (jsonResult.contains("true")) exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                else exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream responseBody = exchange.getResponseBody();
                writeString(jsonResult, responseBody);
                responseBody.close();

            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    protected String processRequest(String requestData) {
        return null;
    }
}
