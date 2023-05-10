import Handler.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Backend of the Family Map Server using http. Uses the correct handler for
 * each request made by the user.
 */
public class Server {

    /**
     * The max number of connections that will wait to connect to the server.
     */
    private static final int MAX_WAITING_CONNECTIONS = 12;

    /**
     * Object of the http server.
     */
    private HttpServer httpServer;

    /**
     * Sets up and runs the server.
     *
     * @param args The argument input becomes the port number.
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }

    /**
     * Sets up the server and runs teh handlers based on what the user inputs
     * through the html page.
     *
     * @param portNumber Selects the port and runs it on the localhost.
     */
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server...");

        try {
            httpServer = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        httpServer.setExecutor(null);
        System.out.println("Creating contexts");

        httpServer.createContext("/user/register", new RegisterHandler());
        httpServer.createContext("/user/login", new LoginHandler());
        httpServer.createContext("/clear", new ClearHandler());
        httpServer.createContext("/person/", new PersonIDHandler());
        httpServer.createContext("/person", new PersonHandler());
        httpServer.createContext("/event/", new EventIDHandler());
        httpServer.createContext("/event", new EventHandler());
        httpServer.createContext("/load", new LoadHandler());
        httpServer.createContext("/fill/", new FillHandler());
        httpServer.createContext("/", new FileHandler());

        System.out.println("Starting server...");
        httpServer.start();
        System.out.println("Server started on port " + portNumber);
    }
}
