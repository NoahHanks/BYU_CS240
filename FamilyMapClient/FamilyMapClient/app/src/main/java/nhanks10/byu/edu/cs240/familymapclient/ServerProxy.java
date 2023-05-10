package nhanks10.byu.edu.cs240.familymapclient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Event;
import Model.Person;
import Request.*;
import Result.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class ServerProxy {
    String serverHost;
    String serverPort;

    public ServerProxy(String serverHost, String serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public LoginResult login(LoginRequest loginRequest) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("username", loginRequest.getUsername());
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            String reqData = "{'username': '" + loginRequest.getUsername() + "', 'password': '" + loginRequest.getPassword() + "'}";

            OutputStream reqBody = http.getOutputStream();
            reqBody.write(reqData.getBytes());
            reqBody.close();

            LoginResult result;
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(respBody);
                Gson gson = new Gson();
                result = gson.fromJson(reader, LoginResult.class);
                System.out.print("Successfully logged in");
            } else {
                InputStream respBody = http.getErrorStream();
                InputStreamReader reader = new InputStreamReader(respBody);
                Gson gson = new Gson();
                result = gson.fromJson(reader, LoginResult.class);
                System.out.print("ERROR: " + result.getMessage());
            }
            return result;
        } catch (MalformedURLException e) {
            System.out.print("There was an error making the URL for the login");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("There was an error making the HTTP connection in the Login function");
            e.printStackTrace();
        }

        LoginResult result = new LoginResult(null, false, null, null, null);
        result.setSuccess(false);
        result.setMessage("Error logging in.");
        return result;
    }

    public GenericResult register(RegisterRequest registerRequest) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            String reqData = "{'username': '" + registerRequest.getUsername() + "', 'password': '" + registerRequest.getPassword() + "', 'email': '" + registerRequest.getEmail() + "', 'firstName': '" + registerRequest.getFirstName() + "', 'lastName': '" + registerRequest.getLastName() + "', 'gender': '" + registerRequest.getGender() + "'}";

            OutputStream reqBody = http.getOutputStream();
            reqBody.write(reqData.getBytes());
            reqBody.close();

            GenericResult result;
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(respBody);
                Gson gson = new Gson();
                result = gson.fromJson(reader, GenericResult.class);
                System.out.println("Register Successful");
                return result;
            } else {
                InputStream respBody = http.getErrorStream();
                InputStreamReader reader = new InputStreamReader(respBody);
                Gson gson = new Gson();
                result = gson.fromJson(reader, GenericResult.class);
                System.out.print("ERROR: " + result.getMessage());
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PersonResult getPeople(String authToken) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setRequestProperty("Authorization", authToken);
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
            InputStream inputStream;
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            } else {
                Log.e("API", "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
                inputStream = connection.getErrorStream();
            }
            return parsePeopleResponse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return new PersonResult("Error", false, null);
        }
    }

    public EventResult getEvents(String authToken) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setRequestProperty("Authorization", authToken);
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return parseEventsResponse(inputStream);
            } else {
                Log.e("API", "Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
                InputStream inputStream = connection.getErrorStream();
                return parseEventsResponse(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new EventResult(null, false, null);
        }
    }

    private PersonResult parsePeopleResponse(InputStream inputStream) throws IOException {
        String jsonString = readString(inputStream);
        Gson gson = new Gson();
        List<Person> people = gson.fromJson(jsonString, new TypeToken<List<Person>>() {
        }.getType());
        return new PersonResult("Success", true, people);
    }

    private EventResult parseEventsResponse(InputStream inputStream) throws IOException {
        String jsonString = readString(inputStream);
        Gson gson = new Gson();
        List<Event> events = gson.fromJson(jsonString, new TypeToken<List<Event>>() {
        }.getType());
        return new EventResult("Success", true, events);
    }

    private String readString(InputStream inputStream) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        reader.close();
        inputStream.close();
        return stringBuilder.toString();
    }


    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String readStringFromInputStream(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[1024];
        int length;
        while ((length = inputStreamReader.read(buffer)) > 0) {
            stringBuilder.append(buffer, 0, length);
        }
        return stringBuilder.toString();
    }
    
}