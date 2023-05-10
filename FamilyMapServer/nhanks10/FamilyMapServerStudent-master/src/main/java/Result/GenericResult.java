package Result;

/**
 * The generic result class for sending messages and a flag for success for fail.
 */
public class GenericResult {
    /**
     * Result message.
     */
    private String message;
    /**
     * Flag for if the result was successful.
     */
    private boolean success;

    /**
     * Constructor for generic results with a message and success flag.
     *
     * @param message error message if request failed or success message if the request succeeded
     * @param success whether the request succeeded or not
     */
    public GenericResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
