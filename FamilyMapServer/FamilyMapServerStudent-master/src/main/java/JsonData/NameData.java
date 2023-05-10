package JsonData;

/**
 * Class to import the name data from the json files.
 */
public class NameData {
    /**
     * An array to hold the names.
     */
    private String[] data;

    /**
     * Creates the array to hold names.
     *
     * @param names An array of strings.
     */
    public NameData(String[] names) {
        this.data = names;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] names) {
        this.data = names;
    }
}
