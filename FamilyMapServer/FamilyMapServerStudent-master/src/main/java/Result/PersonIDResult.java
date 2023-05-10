package Result;

/**
 * Makes a result when a single person is requested from the database.
 */
public class PersonIDResult extends GenericResult {

    /**
     * String, not null, unique.
     */
    private String personID;
    /**
     * String, not null.
     */
    private String associatedUsername;

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
     * String, optional.
     */
    private String fatherID;

    /**
     * String, optional.
     */
    private String motherID;

    /**
     * String, optional.
     */
    private String spouseID;

    /**
     * Makes a result message for a successful person creation.
     *
     * @param message            String message
     * @param success            Boolean success
     * @param personID           String related to the ID of the person involved.
     * @param associatedUsername String of the username associated with the event.
     * @param firstName          String of the first name.
     * @param lastName           String of the last name.
     * @param gender             String of the gender.
     * @param fatherID           String of the father's ID.
     * @param motherID           String of the mother's ID.
     * @param spouseID           String of the spouse's ID.
     */
    public PersonIDResult(String message, boolean success, String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        super(message, success);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public PersonIDResult(String message) {
        super(message, false);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
