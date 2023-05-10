package Request;

/**
 * Produces requests to fill generations.
 */
public class FillRequest {

    /**
     * The default amount of generations to fill.
     */
    private final int DEFAULT_GEN_COUNT = 4;
    /**
     * String for the user's username, not null.
     */
    private String username;
    /**
     * Int for the generation count to fill.
     */
    private int genCount;


    /**
     * Creates a fill request using the username and generation count.
     *
     * @param username user's username
     * @param genCount number of generations to fill
     */
    public FillRequest(String username, int genCount) {
        this.username = username;
        this.genCount = genCount;
    }

    /**
     * Creates a fill request using the username and the default generation count of 4.
     *
     * @param username user's username
     */
    public FillRequest(String username) {
        this.username = username;
        this.genCount = DEFAULT_GEN_COUNT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenCount() {
        return genCount;
    }

    public void setGenCount(int generations) {
        this.genCount = generations;
    }

}
