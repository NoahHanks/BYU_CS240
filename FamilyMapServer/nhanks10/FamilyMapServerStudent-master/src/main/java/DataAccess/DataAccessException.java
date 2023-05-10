package DataAccess;

/**
 * Creates exceptions with messages when trying to access the data from the database.
 */
public class DataAccessException extends Exception {

    /**
     * Creates a DAO exception with an error message.
     *
     * @param message error message
     */
    public DataAccessException(String message) {
        super(message);
    }
}
