package Model;

import java.util.Objects;

/**
 * Class for the model of Family Map users.
 */
public class User {

    /**
     * String, not null, unique.
     */
    private String username;

    /**
     * String, not null.
     */
    private String password;

    /**
     * String, not null.
     */
    private String email;

    /**
     * String, not null.
     */
    private String firstName;

    /**
     * String, not null.
     */
    private String lastName;

    /**
     * String, not null, "m" or "f".
     */
    private String gender;

    /**
     * String, not null.
     */
    private String personID;

    /**
     * Creates a user with a username, password, email, first name, last name, gender, and person id
     *
     * @param username  String, unique, of the username.
     * @param password  String of the password.
     * @param email     String of the email.
     * @param firstName String of the first name.
     * @param lastName  String of the last name.
     * @param gender    String of the gender.
     * @param personID  String of the person ID created for the user.
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Overrides the equals() method.
     *
     * @param o User Object
     * @return True if objects are equal. False if objects aren't equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(personID, user.personID);
    }
}
