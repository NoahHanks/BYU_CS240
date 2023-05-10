package Model;

import java.util.Objects;

/**
 * Class for the model of authtokens used in the database.
 */
public class AuthToken {

    /**
     * String, not null, unique authtoken.
     */
    private String authtoken;

    /**
     * String, not null username.
     */
    private String username;

    /**
     * Creates an Authtoken model with an authtoken and username.
     *
     * @param authtoken String of the authtoken.
     * @param username  String of the username.
     */
    public AuthToken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Overrides the equals() method.
     *
     * @param o AuthToken Object
     * @return True if objects are equal. False if objects aren't equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(authtoken, authToken.authtoken)
                && Objects.equals(username, authToken.username);
    }
}
