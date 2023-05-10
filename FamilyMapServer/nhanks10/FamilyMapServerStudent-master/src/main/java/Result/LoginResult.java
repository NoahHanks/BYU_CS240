package Result;

/**
 * Makes a result when the user tries to log in or register an account.
 */
public class LoginResult extends GenericResult {

    /**
     * String, not null, unique authtoken.
     */
    private String authtoken;

    /**
     * String, not null username.
     */
    private String username;

    /**
     * String, not null, unique personID.
     */
    private String personID;

    /**
     * Sends the result with a message and success flag.
     *
     * @param message   String message
     * @param success   Boolean success flag
     * @param authtoken user's authtoken
     * @param username  user's username
     * @param personID  String related to the ID of the user.
     */
    public LoginResult(String message, boolean success, String authtoken, String username, String personID) {
        super(message, success);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Sends the result with a message and failure flag.
     *
     * @param message error message
     */
    public LoginResult(String message) {
        super(message, false);
        this.authtoken = null;
        this.username = null;
        this.personID = null;
    }

    public LoginResult(String message, boolean success) {
        super(message, success);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
