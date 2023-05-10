package Request;

/**
 * Makes a login request when the user tries to log in.
 */
public class LoginRequest {

    /**
     * String for the user's username, not null.
     */
    private String username;

    /**
     * String for the user's password, not null.
     */
    private String password;

    /**
     * Makes a login request using the username and password.
     *
     * @param username user's username
     * @param password user's password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
}
