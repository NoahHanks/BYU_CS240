package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;

/**
 * Produces requests to load the database with information of various model objects.
 */
public class LoadRequest {

    /**
     * Array List for all the users.
     */
    private ArrayList<User> users;

    /**
     * Array List for all the persons.
     */
    private ArrayList<Person> persons;

    /**
     * Array List for all the events.
     */
    private ArrayList<Event> events;

    /**
     * Makes a request to load the database with information from arrays.
     *
     * @param users   array users
     * @param persons array persons
     * @param events  array events
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

}
