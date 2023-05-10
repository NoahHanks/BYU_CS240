package Request;

/**
 * Makes a request when teh user tries to register a new account.
 */
public class RegisterRequest {

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
     * Makes a registration request using all of the info from the user.
     * The information includes the username, password, email, first and last names, and gender.
     *
     * @param username  String username
     * @param password  String password
     * @param email     String email
     * @param firstName String first name
     * @param lastName  String last name
     * @param gender    String gender
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
}
