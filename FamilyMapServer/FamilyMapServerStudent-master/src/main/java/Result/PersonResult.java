package Result;

import Model.Person;

import java.util.List;

/**
 * Makes a result for when the client requests all the persons.
 */
public class PersonResult extends GenericResult {

    /**
     * List of persons.
     */
    private List<Person> data;

    /**
     * Sends the result with all the persons as well as a message and success flag.
     *
     * @param message String message
     * @param success Boolean success flag
     * @param persons List of persons
     */
    public PersonResult(String message, boolean success, List<Person> persons) {
        super(message, success);
        this.data = persons;
    }

    public PersonResult(String message) {
        super(message, false);
    }

    public List<Person> getPersons() {
        return data;
    }

    public void setPersons(List<Person> persons) {
        this.data = persons;
    }
}
